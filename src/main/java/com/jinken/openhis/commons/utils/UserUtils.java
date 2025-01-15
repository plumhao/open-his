package com.jinken.openhis.commons.utils;

import cn.dev33.satoken.stp.StpUtil;
import com.jinken.openhis.commons.constant.Constants;
import com.jinken.openhis.modules.system.entity.User;

public class UserUtils {

    public static User getLoginUser(){
        //获取当前登录用户
        return (User) StpUtil.getSession().get(Constants.LOGIN_USER);
    }
}
