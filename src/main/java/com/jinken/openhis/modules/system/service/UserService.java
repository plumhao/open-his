package com.jinken.openhis.modules.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jinken.openhis.modules.system.dto.LoginDto;
import com.jinken.openhis.modules.system.dto.UserDto;
import com.jinken.openhis.modules.system.entity.Menu;
import com.jinken.openhis.modules.system.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jinken.openhis.modules.system.vo.ActiveUser;

import java.util.List;

/**
* @author jinken
* @description 针对表【sys_user(用户信息表)】的数据库操作Service
* @createDate 2025-01-07 11:21:41
*/
public interface UserService extends IService<User> {
    /**
     * 登录方法
     * @param loginDto
     * @return
     */
    User login(LoginDto loginDto);

    /**
     * 获取登录用户信息
     * @return
     */
    ActiveUser getActiveUser();

    /**
     * 获取等于用户的菜单
     * @return
     */
    List<Menu> getMenus();

    /**
     * 分页搜索用户
     * @param params
     * @return
     */
    Page<User> listForPage(UserDto params);

    /**
     * 新增用户
     * @param params
     */
    void addUser(UserDto params);

    /**
     * 修改用户
     * @param params
     */
    void updateUser(UserDto params);

    /**
     * 重置密码
     * @param userId
     */
    void resetPwd(Long userId);
}
