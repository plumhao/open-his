package com.jinken.openhis.modules.system.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 部门/科室表
 * @TableName sys_dept
 */
@TableName(value ="sys_dept")
@Data
public class Dept implements Serializable {
    /**
     * 部门科室id
     */
    @TableId(type = IdType.AUTO)
    private Long deptId;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 挂号编号
     */
    private Integer regNumber;

    /**
     * 科室编号
     */
    private String deptNumber;

    /**
     * 显示顺序
     */
    private Integer orderNum;

    /**
     * 负责人
     */
    private String deptLeader;

    /**
     * 联系电话
     */
    private String leaderPhone;

    /**
     * 部门状态（0正常 1停用）
     */
    private String status;

    /**
     * 删除标志（0正常 1删除）
     */
    @TableLogic(delval = "1",value = "0")
    private String delFlag;

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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
