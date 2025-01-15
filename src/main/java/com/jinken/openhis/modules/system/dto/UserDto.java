package com.jinken.openhis.modules.system.dto;


import com.jinken.openhis.commons.dto.BaseDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


/**
 * @Author:
 */

/**
 * 用户信息表
 */
@Data
@EqualsAndHashCode(callSuper=true)
@AllArgsConstructor
@NoArgsConstructor
public class UserDto extends BaseDto {
    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 部门ID
     */
    @NotNull(message = "用户所属部门不能为空")
    private Long deptId;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String userName;

    /**
     * 用户性别（0男 1女 2未知）
     */
    @NotBlank(message = "用户性别不能为空")
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
    @NotBlank(message = "用户学历不能为空")
    private String background;

    /**
     * 电话
     */
    @NotBlank(message = "用户电话不能为空")
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
    @NotBlank(message = "医生级别不能为空")
    private String userRank;


    /**
     * 帐号状态（0正常 1停用）
     */
    @NotBlank(message = "帐号状态不能为空")
    private String status;

    /**
     * 是否需要参与排班0需要,1 不需要
     */
    @NotBlank(message = "是否需要参与排班不能为空")
    private String schedulingFlag;
}
