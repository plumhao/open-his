package com.jinken.openhis;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;

public class LoginTest {

    public static void main(String[] args) {


//        for (int i = 0; i < 10; i++) {
//            String salt = RandomUtil.randomString(10);
//            System.out.println("salt = " + salt);
//        }

        String password = "123456"+"e10adc3949ba59abbe56e057f20f883e";

        String md5Password = SecureUtil.md5(password);
        System.out.println("md5Password = " + md5Password);
    }
}
