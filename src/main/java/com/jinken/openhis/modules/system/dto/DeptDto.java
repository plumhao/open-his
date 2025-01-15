package com.jinken.openhis.modules.system.dto;


import com.jinken.openhis.commons.dto.BaseDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


/**
 * 部门/科室表dto
 */
@Data
@EqualsAndHashCode(callSuper=true)
@AllArgsConstructor
@NoArgsConstructor
public class DeptDto extends BaseDto {
    /**
     * 部门科室id
     */
    private Long deptId;


    /**
     * 部门名称
     */
    @NotBlank(message = "科室名称不能为空")
    private String deptName;


    /**
     * 挂号编号
     */
    @NotNull(message = "挂号编号不能为空")
    private Integer regNumber;


    /**
     * 科室编号
     */
    @NotBlank(message = "科室编号不能为空")
    private String deptNumber;


    /**
     * 显示顺序
     */
    @NotNull(message = "科室顺序不能为空")
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
    @NotBlank(message = "科室状态不能为空")
    private String status;
}
