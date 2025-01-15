package com.jinken.openhis.modules.system.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jinken.openhis.commons.constant.Constants;
import lombok.Data;

/**
 * 角色信息表
 *
 * @TableName sys_role
 */
@TableName(value = "sys_role")
@Data
public class Role implements Serializable {
    /**
     * 角色ID
     */
    @TableId(type = IdType.AUTO)
    private Long roleId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色权限编码
     */
    private String roleCode;

    /**
     * 显示顺序
     */
    private Integer roleSort;

    /**
     * 备注
     */
    private String remark;

    /**
     * 角色状态（0正常 1停用）
     */
    private String status;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = Constants.YYYY_MM_DD)
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
     * 删除标志（0代表存在 2代表删除）
     */
    @TableLogic(value = "0", delval = "1")
    private String delFlag;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
