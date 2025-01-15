package com.jinken.openhis.modules.system.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jinken.openhis.commons.vo.AjaxResult;
import com.jinken.openhis.modules.system.dto.LoginInfoDto;
import com.jinken.openhis.modules.system.entity.LoginInfo;
import com.jinken.openhis.modules.system.service.LoginInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/system/loginInfo")
public class LoginInfoController {

    @Autowired
    private LoginInfoService loginInfoService;

    /**
     * 分页搜索登录日志
     * @param params
     * @return
     */
    @GetMapping("/listForPage")
    public AjaxResult listForPage(LoginInfoDto params){

        Page<LoginInfo> resultPage = loginInfoService.listForPage(params);

        return AjaxResult.success(resultPage.getRecords(),resultPage.getTotal());
    }

    /**
     * 根据id删除登录日志
     * @param loginIds
     * @return
     */
    @DeleteMapping("/deleteLoginInfoByIds/{loginIds}")
    public AjaxResult deleteLoginInfoByIds(@PathVariable Long[] loginIds){

        loginInfoService.removeBatchByIds(Arrays.asList(loginIds));

        return AjaxResult.success();
    }
    /**
     * 清空登录日志
     * @return
     */
    @DeleteMapping("/clearLoginInfo")
    public AjaxResult clearLoginInfo(){

        loginInfoService.remove(null);

        return AjaxResult.success();
    }
}
