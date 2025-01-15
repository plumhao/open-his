package com.jinken.openhis.modules.system.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jinken.openhis.commons.vo.AjaxResult;
import com.jinken.openhis.modules.system.dto.RoleDto;
import com.jinken.openhis.modules.system.entity.Role;
import com.jinken.openhis.modules.system.entity.Role;
import com.jinken.openhis.modules.system.service.RoleMenuService;
import com.jinken.openhis.modules.system.service.RoleService;
import com.jinken.openhis.modules.system.service.RoleUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/system/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleUserService roleUserService;

    @Autowired
    private RoleMenuService roleMenuService;


    /**
     * 分页搜索角色
     * @param params
     * @return
     */
    @GetMapping("/listRoleForPage")
    public AjaxResult listForPage(RoleDto params){

        Page<Role> rolePage = roleService.listForPage(params);


        return AjaxResult.success(rolePage.getRecords(),rolePage.getTotal());

    }


    /**
     * 新增角色
     * @param params
     * @return
     */
    @PostMapping("/addRole")
    public AjaxResult addRole(@Valid RoleDto params){

        roleService.addRole(params);

        return AjaxResult.success();
    }

    /**
     * 修改角色
     * @param params
     * @return
     */
    @PutMapping("/updateRole")
    public AjaxResult updateRole(@Valid RoleDto params){

        roleService.updateRole(params);

        return AjaxResult.success();
    }

    /**
     * 根据id查询角色信息
     * @param roleId
     * @return
     */
    @GetMapping("/getRoleById/{roleId}")
    public AjaxResult getRoleById(@PathVariable Long roleId){
        return AjaxResult.success(roleService.getById(roleId));
    }

    /**
     * 根据id删除角色
     * @param roleIds
     * @return
     */
    @DeleteMapping("/deleteRoleByIds/{roleIds}")
    public AjaxResult deleteDeptByIds(@PathVariable Long[] roleIds){
        roleService.deleteDeptByIds(roleIds);
        return AjaxResult.success();
    }

    @GetMapping("/selectAllRole")
    public AjaxResult selectAllRole(){

        List<Role> roles = roleService.selectAllRole();

        return AjaxResult.success(roles);

    }

    /**
     * 获取当前用户的角色id
     * @param userId
     * @return
     */
    @GetMapping("/getRoleIdsByUserId/{userId}")
    public AjaxResult getRoleIdsByUserId(@PathVariable Long userId){

        List<Long> roleIds = roleUserService.getRoleIdsByUserId(userId);

        return AjaxResult.success(roleIds);

    }

    /**
     * 为用户分配角色
     * @param userId 用户id
     * @param roleIds 角色id集合
     * @return
     */
    @PostMapping("/saveRoleUser/{userId}/{roleIds}")
    public AjaxResult saveRoleUser(@PathVariable Long userId,@PathVariable Long[] roleIds){

        roleUserService.saveRoleUser(userId,roleIds);

        return AjaxResult.success();
    }

    /**
     * 为角色分配菜单
     * @param roleId 角色id
     * @param menuIds 菜单id集合
     * @return
     */
    @PostMapping("/saveRoleMenu/{roleId}/{menuIds}")
    public AjaxResult saveRoleMenu(@PathVariable Long roleId,@PathVariable Long[] menuIds){

        roleMenuService.saveRoleMenu(roleId,menuIds);

        return AjaxResult.success();
    }
}
