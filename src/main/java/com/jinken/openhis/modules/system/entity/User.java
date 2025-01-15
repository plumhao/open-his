package com.jinken.openhis.modules.system.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 用户信息表
 * @TableName sys_user
 */
@TableName(value ="sys_user")
@Data
public class User implements Serializable {
    /**
     * 用户ID
     */
    @TableId(type = IdType.AUTO)
    private Long userId;

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 用户账号
     */
    private String userName;

    /**
     * 用户类型（0超级用户为 1为系统用户）
     */
    private String userType;

    /**
     * 用户性别（0男 1女 2未知）
     */
    private String sex;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 头像
     */
    private String picture;

    /**
     * 学历 sys_dict_type:sys_user_background
     */
    private String background;

    /**
     * 电话
     */
    private String phone;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 擅长
     */
    private String strong;

    /**
     * 荣誉
     */
    private String honor;

    /**
     * 简介
     */
    private String introduction;

    /**
     *
医生级别sys_dict_type:sys_user_level
     */
    private String userRank;

    /**
     * 密码
     */
    private String password;

    /**
     * 最后一次登录时间
     */
    private Date lastLoginTime;

    /**
     * 最后登陆IP
     */
    private String lastLoginIp;

    /**
     * 帐号状态（0正常 1停用）
     */
    private String status;

    /**
     *
     */
    private String unionId;

    /**
     * 用户授权登录openid 扩展第三方登陆使用
     */
    private String openId;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 盐
     */
    private String salt;

    /**
     * 删除标志（0正常 1删除）
     */
    @TableLogic(value = "0",delval = "1")
    private String delFlag;

    /**
     * 是否需要参与排班0需要,1 不需要
     */
    private String schedulingFlag;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
