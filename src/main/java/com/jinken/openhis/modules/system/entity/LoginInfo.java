package com.jinken.openhis.modules.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 系统访问记录
 * @TableName sys_login_info
 */
@TableName(value ="sys_login_info")
@Data
public class LoginInfo implements Serializable {
    /**
     * 访问ID
     */
    @TableId(type = IdType.AUTO)
    private Long infoId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 登陆账号
     */
    private String loginAccount;

    /**
     * 登录IP地址
     */
    private String ipAddr;

    /**
     * 登录地点
     */
    private String loginLocation;

    /**
     * 浏览器类型
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 登录状态（0成功 1失败）字典表
     */
    private String loginStatus;

    /**
     * 登陆类型0系统用户1患者用户 字典表
     */
    private String loginType;

    /**
     * 提示消息
     */
    private String msg;

    /**
     * 访问时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date loginTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
