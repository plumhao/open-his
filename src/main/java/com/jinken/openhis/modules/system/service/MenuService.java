package com.jinken.openhis.modules.system.service;

import com.jinken.openhis.modules.system.dto.MenuDto;
import com.jinken.openhis.modules.system.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author jinken
* @description 针对表【sys_menu(菜单权限表)】的数据库操作Service
* @createDate 2025-01-07 11:21:42
*/
public interface MenuService extends IService<Menu> {

    List<Menu> selectMenuTree();

    List<Long> getMenuIdsByRoleId(Long roleId);

    List<Menu> listAllMenus(MenuDto params);

    void addMenu(MenuDto params);

    void updateMenu(MenuDto params);

    void deleteDeptById(Long menuId);
}
