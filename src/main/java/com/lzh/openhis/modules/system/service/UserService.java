package com.lzh.openhis.modules.system.service;

import com.lzh.openhis.modules.system.dto.LoginDto;
import com.lzh.openhis.modules.system.entity.Menu;
import com.lzh.openhis.modules.system.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lzh.openhis.modules.system.vo.ActiveUser;

import java.util.List;

/**
 * @author HP
 * @description 针对表【sys_user(用户信息表)】的数据库操作Service
 * @createDate 2025-01-07 11:21:41
 */
public interface UserService extends IService<User> {
    /**
     * 登录方法
     *
     * @param loginDto
     * @return
     */
    User login(LoginDto loginDto);

    /**
     * 获取登录用户信息
     *
     * @return
     */
    ActiveUser getActiveUser();

    /**
     * 获取等于用户的菜单
     *
     * @return
     */
    List<Menu> getMenus();
}

