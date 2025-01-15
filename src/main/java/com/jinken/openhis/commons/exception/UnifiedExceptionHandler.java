package com.jinken.openhis.commons.exception;

import cn.dev33.satoken.exception.NotPermissionException;
import com.jinken.openhis.commons.vo.AjaxResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @RestControllerAdvice
 * 声明当前控制器为异常处理控制器，可以捕获异常，并处理
 */
@RestControllerAdvice
public class UnifiedExceptionHandler {

    /**
     * 捕获业务异常的方法
     * @param exception
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    public AjaxResult handleBusinessException(BusinessException exception){
        Integer code = exception.getCode();
        String message = exception.getMessage();
        return  AjaxResult.error(code,message);
    }


    /**
     * 校验异常处理
     * @param ex
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public AjaxResult handlerVaidate(MethodArgumentNotValidException ex){
        FieldError fieldError = ex.getBindingResult().getFieldError();
        return AjaxResult.error(fieldError.getDefaultMessage());
    }
    /**
     * 捕获没有权限异常
     * @param ex
     * @return
     */
    @ExceptionHandler(NotPermissionException.class)
    public AjaxResult handlerNotPermission(NotPermissionException ex){
        return AjaxResult.error(ex.getCode(),ex.getMessage());
    }
}
