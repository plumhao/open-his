package com.jinken.openhis.modules.system.service;

import com.jinken.openhis.modules.system.entity.RoleUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author jinken
* @description 针对表【sys_role_user(用户和角色关联表)】的数据库操作Service
* @createDate 2025-01-07 11:21:41
*/
public interface RoleUserService extends IService<RoleUser> {
    /**
     * 根据用户id查询出对应的角色的id
     * @param userId 用户id
     * @return
     */
    List<Long> getRoleIdsByUserId(Long userId);

    /**
     * 为用户分配角色
     * @param userId 用户id
     * @param roleIds 角色id集合
     */
    void saveRoleUser(Long userId, Long[] roleIds);


}
