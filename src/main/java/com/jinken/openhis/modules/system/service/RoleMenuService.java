package com.jinken.openhis.modules.system.service;

import com.jinken.openhis.modules.system.entity.RoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author jinken
* @description 针对表【sys_role_menu(角色和菜单关联表)】的数据库操作Service
* @createDate 2025-01-07 11:21:41
*/
public interface RoleMenuService extends IService<RoleMenu> {

    /**
     * 为加速额分配菜单
     * @param roleId 角色id
     * @param menuIds 菜单id集合
     */
    void saveRoleMenu(Long roleId, Long[] menuIds);
}
