package com.jinken.openhis.modules.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinken.openhis.modules.system.entity.RoleMenu;
import com.jinken.openhis.modules.system.service.RoleMenuService;
import com.jinken.openhis.modules.system.mapper.RoleMenuMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
* @author jinken
* @description 针对表【sys_role_menu(角色和菜单关联表)】的数据库操作Service实现
* @createDate 2025-01-07 11:21:41
*/
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu>
    implements RoleMenuService{

    @Override
    @Transactional
    public void saveRoleMenu(Long roleId, Long[] menuIds) {
        /**
         * 分配权限思路
         * 1，先删除 sys_role_menu 中间表对应角色的所有数据
         *
         * 2，新增数据
         */
        LambdaQueryWrapper<RoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RoleMenu::getRoleId,roleId);
        this.remove(queryWrapper);

        List<RoleMenu> roleMenuList = new ArrayList<>();
        for (Long menuId : menuIds) {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuId);

            roleMenuList.add(roleMenu);
        }
        //批量插入
        this.saveBatch(roleMenuList);

    }
}




