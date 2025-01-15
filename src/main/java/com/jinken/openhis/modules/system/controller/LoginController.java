package com.jinken.openhis.modules.system.controller;


import cn.dev33.satoken.stp.StpUtil;
import com.jinken.openhis.commons.constant.Constants;
import com.jinken.openhis.commons.utils.ServletUtils;
import com.jinken.openhis.commons.vo.AjaxResult;
import com.jinken.openhis.modules.system.dto.LoginDto;
import com.jinken.openhis.modules.system.entity.Menu;
import com.jinken.openhis.modules.system.entity.User;
import com.jinken.openhis.modules.system.service.UserService;
import com.jinken.openhis.modules.system.vo.ActiveUser;
import com.jinken.openhis.modules.system.vo.MenuTreeVo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Enumeration;
import java.util.List;
import java.util.Objects;

@RestController
public class LoginController {

    @Autowired
    private UserService userService;



    @PostMapping("/login/doLogin")
    public AjaxResult doLogin(@RequestBody @Valid LoginDto loginDto){

        User user = userService.login(loginDto);

        if(Objects.isNull(user)){
            return AjaxResult.error("账号密码错误");
        }
        //使用SaToken登录
        StpUtil.login(user.getUserId());
        //存储用户信息
        StpUtil.getSession().set(Constants.LOGIN_USER,user);

        AjaxResult success = AjaxResult.success();
        success.put(Constants.TOKEN,StpUtil.getTokenValue());

        return success;
    }


    @GetMapping("/login/getInfo")
    public AjaxResult getInfo(){

        //获取登录用户的信息
        ActiveUser activeUser = userService.getActiveUser();

        AjaxResult ajaxResult = AjaxResult.success();
        ajaxResult.put("username",activeUser.getUser().getUserName());
        ajaxResult.put("picture",activeUser.getUser().getPicture());
        ajaxResult.put("roles",activeUser.getRoles());
        ajaxResult.put("permissions",activeUser.getPermissions());

        return ajaxResult;
    }

    /**
     * 获取当前登录用户的菜单
     * @return
     */
    @GetMapping("/login/getMenus")
    public AjaxResult getMenus(){

        List<Menu>  menus = userService.getMenus();

        //转换菜单
        List<MenuTreeVo> menuTreeVoList = menus.stream().map(menu -> {
            MenuTreeVo menuTreeVo = new MenuTreeVo();
            menuTreeVo.setId(menu.getMenuId());
            menuTreeVo.setSerPath(menu.getPath());
            return menuTreeVo;
        }).toList();

        return AjaxResult.success(menuTreeVoList);

    }
    /**
     * 退出登录
     */
    @PostMapping("/login/logout")
    public AjaxResult logout(){
        //SaToken退出登录，清空当前登录用户session redis cookie 数据
        StpUtil.logout();

        return AjaxResult.success();
    }
}
