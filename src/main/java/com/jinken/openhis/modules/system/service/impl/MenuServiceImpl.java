package com.jinken.openhis.modules.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinken.openhis.commons.constant.Constants;
import com.jinken.openhis.commons.enums.ErrorCodeAndMessageEnum;
import com.jinken.openhis.commons.exception.BusinessException;
import com.jinken.openhis.commons.utils.UserUtils;
import com.jinken.openhis.modules.system.dto.MenuDto;
import com.jinken.openhis.modules.system.entity.Menu;
import com.jinken.openhis.modules.system.entity.RoleMenu;
import com.jinken.openhis.modules.system.entity.User;
import com.jinken.openhis.modules.system.mapper.RoleMenuMapper;
import com.jinken.openhis.modules.system.service.MenuService;
import com.jinken.openhis.modules.system.mapper.MenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.jinken.openhis.commons.enums.ErrorCodeAndMessageEnum.*;

/**
 * @author jinken
 * @description 针对表【sys_menu(菜单权限表)】的数据库操作Service实现
 * @createDate 2025-01-07 11:21:42
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu>
        implements MenuService {

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Override
    public List<Menu> selectMenuTree() {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Menu::getStatus, Constants.STATUS_TRUE);
        return this.list(queryWrapper);
    }

    @Override
    public List<Long> getMenuIdsByRoleId(Long roleId) {

        LambdaQueryWrapper<RoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RoleMenu::getRoleId, roleId);

        List<RoleMenu> roleMenus = roleMenuMapper.selectList(queryWrapper);

        //转换成菜单id
        List<Long> menuIds = roleMenus.stream().map(RoleMenu::getMenuId).toList();

        return menuIds;
    }

    @Override
    public List<Menu> listAllMenus(MenuDto params) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StrUtil.isNotBlank(params.getMenuName()), Menu::getMenuName, params.getMenuName());
        queryWrapper.eq(StrUtil.isNotBlank(params.getStatus()), Menu::getStatus, params.getStatus());
        return this.list(queryWrapper);
    }

    @Override
    public void addMenu(MenuDto params) {
        Menu menu = new Menu();
        BeanUtil.copyProperties(params, menu);

        User loginUser = UserUtils.getLoginUser();
        menu.setCreateBy(loginUser.getUserName());
        menu.setCreateTime(new Date());
        this.save(menu);
    }

    @Override
    public void updateMenu(MenuDto params) {
        Menu menu = new Menu();
        BeanUtil.copyProperties(params, menu);
        User loginUser = UserUtils.getLoginUser();
        menu.setUpdateBy(loginUser.getUserName());
        menu.setUpdateTime(new Date());
        this.updateById(menu);
    }

    @Override
    public void deleteDeptById(Long menuId) {
        /**
         * 删除菜单思路
         * 1，判断 sys_role_menu 中间表是否有关联的菜单id，如果有，不能删除
         */

        LambdaQueryWrapper<RoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RoleMenu::getMenuId, menuId);
        Long count = roleMenuMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new BusinessException(MENU_HAS_ROLE_ERROR);
        }
        /**
         * 2. 判断当前菜单是否是父菜单（是否拥有子菜单）
         */

        LambdaQueryWrapper<Menu> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Menu::getParentId, menuId);

        long childrenCount = this.count(lambdaQueryWrapper);

        if (childrenCount > 0) {
            throw new BusinessException(MENU_HAS_CHILD_ERROR);
        }


        //删除菜单
        this.removeById(menuId);
    }
}




