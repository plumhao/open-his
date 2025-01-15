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
@Data
@EqualsAndHashCode(callSuper=true)
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto extends BaseDto {
    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称不能为空")
    private String roleName;

    /**
     * 角色权限编码
     */
    @NotBlank(message = "角色编码不能为空")
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
}

