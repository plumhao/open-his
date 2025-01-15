package com.jinken.openhis.modules.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jinken.openhis.commons.constant.Constants;
import com.jinken.openhis.commons.vo.AjaxResult;
import com.jinken.openhis.modules.system.dto.MenuDto;
import com.jinken.openhis.modules.system.dto.MenuDto;
import com.jinken.openhis.modules.system.entity.Menu;
import com.jinken.openhis.modules.system.service.MenuService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/system/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    /**
     * 查询所有的 可用菜单（权限），角色分配菜单适用
     * @return
     */
    @GetMapping("/selectMenuTree")
    public AjaxResult selectMenuTree(){

        List<Menu> menus = menuService.selectMenuTree();

        return AjaxResult.success(menus);
    }

    /**
     * 查询所有菜单
     * @return
     */
    @GetMapping("/listAllMenus")
    public AjaxResult listAllMenus(MenuDto params){

        List<Menu> menus = menuService.listAllMenus(params);

        return AjaxResult.success(menus);
    }

    /**
     * 根据角色的id查询对应的菜单id集合
     * @param roleId
     * @return
     */
    @GetMapping("/getMenuIdsByRoleId/{roleId}")
    public AjaxResult getMenuIdsByRoleId(@PathVariable Long roleId){

        List<Long> menuIds = menuService.getMenuIdsByRoleId(roleId);

        return AjaxResult.success(menuIds);
    }


    /**
     * 新增菜单
     * @param params
     * @return
     */
    @PostMapping("/addMenu")
    public AjaxResult addMenu(@Valid MenuDto params){

        menuService.addMenu(params);

        return AjaxResult.success();
    }

    /**
     * 修改菜单
     * @param params
     * @return
     */
    @PutMapping("/updateMenu")
    public AjaxResult updateMenu(@Valid MenuDto params){

        menuService.updateMenu(params);

        return AjaxResult.success();
    }

    /**
     * 根据id查询菜单信息
     * @param menuId
     * @return
     */
    @GetMapping("/getMenuById/{menuId}")
    public AjaxResult getMenuById(@PathVariable Long menuId){
        return AjaxResult.success(menuService.getById(menuId));
    }

    /**
     * 根据id删除菜单
     * @param menuId
     * @return
     */
    @DeleteMapping("/deleteMenuById/{menuId}")
    public AjaxResult deleteDeptByIds(@PathVariable Long menuId){
        menuService.deleteDeptById(menuId);
        return AjaxResult.success();
    }
}
