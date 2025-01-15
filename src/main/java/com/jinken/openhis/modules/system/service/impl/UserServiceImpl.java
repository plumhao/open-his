package com.jinken.openhis.modules.system.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinken.openhis.commons.constant.Constants;
import com.jinken.openhis.commons.utils.IpUtils;
import com.jinken.openhis.commons.utils.ServletUtils;
import com.jinken.openhis.commons.utils.UserUtils;
import com.jinken.openhis.modules.system.dto.LoginDto;
import com.jinken.openhis.modules.system.dto.UserDto;
import com.jinken.openhis.modules.system.entity.*;
import com.jinken.openhis.modules.system.mapper.LoginInfoMapper;
import com.jinken.openhis.modules.system.mapper.MenuMapper;
import com.jinken.openhis.modules.system.mapper.RoleMapper;
import com.jinken.openhis.modules.system.service.UserService;
import com.jinken.openhis.modules.system.mapper.UserMapper;
import com.jinken.openhis.modules.system.vo.ActiveUser;
import eu.bitwalker.useragentutils.UserAgent;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;

/**
 * @author jinken
 * @description 针对表【sys_user(用户信息表)】的数据库操作Service实现
 * @createDate 2025-01-07 11:21:41
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private LoginInfoMapper loginInfoMapper;

    @Override
    public User login(LoginDto loginDto) {

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhone, loginDto.getUsername());

        User loginUser = this.getOne(queryWrapper);

        //创建登录信息对象
        LoginInfo loginInfo = this.createLoginInfo();
        //设置登录账号（手机号）
        loginInfo.setLoginAccount(loginDto.getUsername());

        if (Objects.nonNull(loginUser)) {
            //设置登录的用户名称
            loginInfo.setUserName(loginUser.getUserName());

            /**
             * 密码比对思路
             * 1，用户输入密码+salt 摘要生成密码
             * 2，生成密码和数据库密码比
             * 相等：登录成功
             * 不相等：账号密码错误
             */
            String password = loginDto.getPassword() + loginUser.getSalt();

            String md5Password = SecureUtil.md5(password);
            //登录成功
            if (loginUser.getPassword().equals(md5Password)) {
                //设置登录信息的状态
                loginInfo.setLoginStatus(Constants.STATUS_TRUE);
                loginInfo.setMsg("登录成功");
                //保存登录日志
                loginInfoMapper.insert(loginInfo);
                return loginUser;
            }


            //设置登录信息的状态
            loginInfo.setLoginStatus(Constants.STATUS_FALSE);
            loginInfo.setMsg("登录失败");
            //保存登录日志
            loginInfoMapper.insert(loginInfo);
        }
        return null;
    }

    /**
     * 创建登录信息对象
     */
    public LoginInfo createLoginInfo() {
        //创建登录信息对象
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setLoginTime(new Date());
        //获取Servlet的请求对象
        HttpServletRequest request = ServletUtils.getRequest();
        //获取所有的请求头以及值
//        Enumeration<String> headerNames = request.getHeaderNames();
//        while (headerNames.hasMoreElements()){
//            String headerName = headerNames.nextElement();
//            String headerValue = request.getHeader(headerName);
//            System.out.println(headerName+" : "+headerValue);
//        }
        //获取请求ip
        String ipAddr = IpUtils.getIpAddr(request);
        loginInfo.setIpAddr(ipAddr);

        //获取User-agent
        String userAgentStr = request.getHeader("user-agent");//Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36 Edg/126.0.0.0
        //使用UserAgent工具类解析
        UserAgent userAgent = UserAgent.parseUserAgentString(userAgentStr);

        //获取浏览器名称
        String browserName = userAgent.getBrowser().getName();
        loginInfo.setBrowser(browserName);

        //获取操作系统
        String osName = userAgent.getOperatingSystem().getName();
        loginInfo.setOs(osName);

        //设置用户类型
        loginInfo.setLoginType(Constants.LOGIN_TYPE_SYSTEM);

        return loginInfo;
    }


    @Override
    public ActiveUser getActiveUser() {

        //获取登录用户
        User user = (User) StpUtil.getSession().get(Constants.LOGIN_USER);


        //创建用户信息对象
        ActiveUser activeUser = new ActiveUser();
        activeUser.setUser(user);

        //超级管理员不用查询角色权限信息，拥有最高权限
        if (!Objects.equals(user.getUserType(), Constants.USER_ADMIN)) {
            //设置角色
            List<Role> roles = roleMapper.selectRolesByUserId(user.getUserId());
            List<String> roleCodes = roles.stream().map(Role::getRoleCode).toList();
            activeUser.setRoles(roleCodes);
        }


        //设置权限
        List<Menu> menus = menuMapper.selectMenusByUserId(user.getUserId());
        List<String> menuNames = menus.stream().map(Menu::getMenuName).toList();
        activeUser.setPermissions(menuNames);

        return activeUser;
    }

    @Override
    public List<Menu> getMenus() {

        //获取登录用户
        User user = (User) StpUtil.getSession().get(Constants.LOGIN_USER);

        Long userId = user.getUserId();
        //超级管理员,查询所有,用户id设置为null
        if (user.getUserType().equals(Constants.USER_ADMIN)) {
            userId = null;
        }

        List<Menu> menus = menuMapper.selectMenusByUserId(userId);

        return menus;
    }

    @Override
    public Page<User> listForPage(UserDto params) {

        Page<User> userPage = new Page<>(params.getPageNum(), params.getPageSize());

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.like(StrUtil.isNotBlank(params.getUserName()), User::getUserName, params.getUserName());
        queryWrapper.like(StrUtil.isNotBlank(params.getPhone()), User::getPhone, params.getPhone());
        queryWrapper.eq(Objects.nonNull(params.getDeptId()), User::getDeptId, params.getDeptId());
        queryWrapper.eq(StrUtil.isNotBlank(params.getStatus()), User::getStatus, params.getStatus());
        queryWrapper.ge(Objects.nonNull(params.getBeginTime()), User::getCreateTime, params.getBeginTime());
        queryWrapper.le(Objects.nonNull(params.getEndTime()), User::getCreateTime, params.getEndTime());
        queryWrapper.orderByDesc(User::getCreateTime);

        //执行查询
        this.page(userPage,queryWrapper);

        return userPage;
    }

    @Override
    public void addUser(UserDto params) {

        //创建科室
        User user = new User();
        BeanUtil.copyProperties(params,user);

        User loginUser = UserUtils.getLoginUser();
        user.setCreateBy(loginUser.getUserName());
        user.setCreateTime(new Date());

        this.save(user);
    }

    @Override
    public void updateUser(UserDto params) {
        //创建科室
        User user = new User();
        BeanUtil.copyProperties(params,user);

        User loginUser = UserUtils.getLoginUser();
        user.setUpdateBy(loginUser.getUserName());
        user.setUpdateTime(new Date());

        this.updateById(user);
    }

    @Override
    public void resetPwd(Long userId) {

        User user = new User();
        user.setUserId(userId);

        //1.生成密码的salt （随机x位数）
        String salt = RandomUtil.randomString(10);
        //设置用户的salt
        user.setSalt(salt);

        String password = "123456" + salt;
        //MD5 摘要加密
        String md5Password = SecureUtil.md5(password);
        //设置用户的密码
        user.setPassword(md5Password);

        this.updateById(user);
    }
}




