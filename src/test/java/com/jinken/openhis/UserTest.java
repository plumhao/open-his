package com.jinken.openhis;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;

public class UserTest {


    public static void main(String[] args) {

        //生成随机数
        for (int i = 0; i < 10; i++) {
            String salt = RandomUtil.randomString(10);
            System.out.println("salt = " + salt);

            String default_password = "123456";

            String password = default_password + salt;

            //MD5 摘要加密
            String md5Password = SecureUtil.md5(password);
            System.out.println("明文密码："+password+ " ， 加密后" + md5Password);
        }





    }

}
