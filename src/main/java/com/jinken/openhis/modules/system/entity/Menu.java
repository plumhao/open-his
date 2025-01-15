package com.jinken.openhis.modules.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jinken.openhis.commons.constant.Constants;
import lombok.Data;

/**
 * 菜单权限表
 * @TableName sys_menu
 */
@TableName(value ="sys_menu")
@Data
public class Menu implements Serializable {
    /**
     * 菜单ID
     */
    @TableId(type = IdType.AUTO)
    private Long menuId;

    /**
     * 父菜单ID
     */
    private Long parentId;

    /**
     * 父节点ID集合
     */
    private String parentIds;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 菜单类型（M目录 C菜单 F按钮）
     */
    private String menuType;

    /**
     * 权限标识
     */
    private String percode;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 备注
     */
    private String remark;

    /**
     * 菜单状态（0正常 1停用）
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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
