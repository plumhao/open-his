package com.jinken.openhis.modules.system.controller;


import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jinken.openhis.commons.vo.AjaxResult;
import com.jinken.openhis.modules.system.dto.UserDto;
import com.jinken.openhis.modules.system.dto.UserDto;
import com.jinken.openhis.modules.system.entity.User;
import com.jinken.openhis.modules.system.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/system/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 分页搜索用户
     * @param params
     * @return
     */
    @GetMapping("/listUserForPage")
    @SaCheckPermission("user:list")
    public AjaxResult listForPage(UserDto params){

        Page<User> userPage = userService.listForPage(params);


        return AjaxResult.success(userPage.getRecords(),userPage.getTotal());

    }


    /**
     * 新增用户
     * @param params
     * @return
     */
    @PostMapping("/addUser")
    @SaCheckPermission("user:add")
    public AjaxResult addUser(@Valid UserDto params){

        userService.addUser(params);

        return AjaxResult.success();
    }

    /**
     * 修改用户
     * @param params
     * @return
     */
    @PutMapping("/updateUser")
    @SaCheckPermission("user:update")
    public AjaxResult updateUser(@Valid UserDto params){

        userService.updateUser(params);

        return AjaxResult.success();
    }

    /**
     * 根据id查询用户信息
     * @param userId
     * @return
     */
    @GetMapping("/getUserById/{userId}")
    public AjaxResult getUserById(@PathVariable Long userId){
        return AjaxResult.success(userService.getById(userId));
    }

    /**
     * 根据id删除用户
     * @param userIds
     * @return
     */
    @DeleteMapping("/deleteUserByIds/{userIds}")
    public AjaxResult deleteDeptByIds(@PathVariable Long[] userIds){
        userService.removeBatchByIds(Arrays.asList(userIds));
        return AjaxResult.success();
    }

    @PostMapping("/resetPwd/{userId}")
    public AjaxResult resetPwd(@PathVariable Long userId){
        userService.resetPwd(userId);
        return AjaxResult.success();
    }

}
