package com.lzh.openhis.modules.system.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzh.openhis.commons.constant.Constants;
import com.lzh.openhis.modules.system.dto.LoginDto;
import com.lzh.openhis.modules.system.entity.Menu;
import com.lzh.openhis.modules.system.entity.Role;
import com.lzh.openhis.modules.system.entity.User;
import com.lzh.openhis.modules.system.mapper.MenuMapper;
import com.lzh.openhis.modules.system.mapper.RoleMapper;
import com.lzh.openhis.modules.system.service.UserService;
import com.lzh.openhis.modules.system.mapper.UserMapper;
import com.lzh.openhis.modules.system.vo.ActiveUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public User login(LoginDto loginDto) {

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhone, loginDto.getUsername());

        User loginUser = this.getOne(queryWrapper);

        if (Objects.nonNull(loginUser)) {

            /**
             * 密码比对思路
             * 1，用户输入密码+salt 摘要生成密码
             * 2，生成密码和数据库密码比
             * 相等：登录成功
             * 不相等：账号密码错误
             */
            String password = loginDto.getPassword() + loginUser.getSalt();

            String md5Password = SecureUtil.md5(password);

            if (loginUser.getPassword().equals(md5Password)) {
                return loginUser;
            }

        }
        return null;
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
}





