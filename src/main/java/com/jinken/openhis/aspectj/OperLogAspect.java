package com.jinken.openhis.aspectj;


import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.jinken.openhis.commons.annotation.Log;
import com.jinken.openhis.commons.constant.Constants;
import com.jinken.openhis.commons.utils.IpUtils;
import com.jinken.openhis.commons.utils.ServletUtils;
import com.jinken.openhis.modules.system.entity.OperLog;
import com.jinken.openhis.modules.system.entity.User;
import com.jinken.openhis.modules.system.service.OperLogService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;

/**
 *
 */
@Component
@Aspect
@Log4j2
public class OperLogAspect {


    @Autowired
    private OperLogService operLogService; //保存日志的接口


    /**
     * 声明切面
     * 只要Controller的方法里面有@Log这个一样注解，那我就要切入
     */
    @Pointcut("@annotation(com.jinken.openhis.commons.annotation.Log)")
    public void logPointCut() {
    }


    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "logPointCut()", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Object jsonResult) {
        handleLog(joinPoint, null, jsonResult);
    }


    /**
     * 拦截异常操作
     *
     * @param joinPoint 切点
     * @param e         异常
     */
    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
        handleLog(joinPoint, e, null);
    }


    protected void handleLog(final JoinPoint joinPoint, final Exception e, Object jsonResult) {
        try {
            // 获得注解
            Log controllerLog = getAnnotationLog(joinPoint);
            if (controllerLog == null) {
                return;
            }
            // *========数据库日志=========*//
            OperLog operLog = new OperLog();
            operLog.setStatus(Constants.STATUS_TRUE);
            // 请求的地址
            String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
            operLog.setOperIp(ip);
            operLog.setOperLocation(ip);
            // 返回参数
            operLog.setJsonResult(JSONUtil.toJsonStr(jsonResult));

            //获取登录用户
            User loginUser = (User) StpUtil.getSession().get(Constants.LOGIN_USER);

            operLog.setOperUrl(ServletUtils.getRequest().getRequestURI());
            if (Objects.nonNull(loginUser)) {
                operLog.setOperName(loginUser.getUserName());
            }


            if (Objects.nonNull(e)) {
                operLog.setStatus(Constants.STATUS_FALSE);
                operLog.setErrorMsg(StrUtil.sub(e.getMessage(), 0, 2000));
            }
            // 设置方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            operLog.setMethod(className + "." + methodName + "()");
            // 设置请求方式
            operLog.setRequestMethod(ServletUtils.getRequest().getMethod());
            // 处理设置注解上的参数
            getControllerMethodDescription(joinPoint, controllerLog, operLog);
            //设置操作时间
            operLog.setOperTime(DateUtil.date());
            // 保存数据库
            operLogService.save(operLog);
        } catch (Exception exp) {
            // 记录本地异常日志
            log.error("==前置通知异常==");
            log.error("异常信息:{}", exp.getMessage());
            exp.printStackTrace();
        }
    }

    /**
     * 获取方法的描述信息
     *
     * @param joinPoint
     * @param controllerLog
     * @param operLog
     */
    private void getControllerMethodDescription(JoinPoint joinPoint, Log controllerLog, OperLog operLog) {
        operLog.setBusinessType(String.valueOf(controllerLog.businessType().ordinal()));
        operLog.setTitle(controllerLog.title());
        operLog.setOperatorType(controllerLog.operatorType().ordinal());
        if (controllerLog.isSaveRequestData()) {
            setRequestValue(joinPoint, operLog);
        }
    }

    /**
     * 获取请求的参数，放到log中
     *
     * @param joinPoint
     * @param operLog
     */
    private void setRequestValue(JoinPoint joinPoint, OperLog operLog) {
        String requestMethod = operLog.getRequestMethod();
        if (HttpMethod.PUT.name().equals(requestMethod) || HttpMethod.POST.name().equals(requestMethod)) {
            String params = argsArrayToString(joinPoint.getArgs());
            operLog.setOperParam(StrUtil.sub(params, 0, 2000));
        } else {
            Map<?, ?> paramsMap = (Map<?, ?>) ServletUtils.getRequest().getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            operLog.setOperParam(StrUtil.sub(paramsMap.toString(), 0, 2000));
        }
    }

    private String argsArrayToString(Object[] args) {
        String params = "";
        if (args != null && args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                if (!isFilterObject(args[i])) {
                    String json = JSONUtil.toJsonStr(args[i]);
                    params += json + " ";
                }
            }
        }
        return params.trim();
    }

    private boolean isFilterObject(Object arg) {
        return arg instanceof MultipartFile || arg instanceof HttpServletRequest || arg instanceof HttpServletResponse;
    }


    /**
     * 是否存在注解，如果存在就获取
     *
     * @param joinPoint
     * @return
     */
    private Log getAnnotationLog(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method != null) {
            return method.getAnnotation(Log.class);
        }
        return null;
    }
}
