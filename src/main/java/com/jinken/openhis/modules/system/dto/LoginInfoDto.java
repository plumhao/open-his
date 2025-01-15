package com.jinken.openhis.modules.system.dto;


import com.jinken.openhis.commons.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Author:
 */

/**
 * 系统访问记录
 */
@Data
@EqualsAndHashCode(callSuper=true)
@AllArgsConstructor
@NoArgsConstructor
public class LoginInfoDto extends BaseDto {

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
     * 登录状态（0成功 1失败）字典表
     */
    private String loginStatus;

    /**
     * 登陆类型0系统用户1患者用户 字典表
     */
    private String loginType;

}
