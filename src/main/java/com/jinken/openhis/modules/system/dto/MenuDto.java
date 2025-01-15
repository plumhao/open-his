package com.jinken.openhis.modules.system.dto;

/**
 * @Author:
 */

import com.jinken.openhis.commons.dto.BaseDto;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 菜单权限表
 */
@Data
@EqualsAndHashCode(callSuper=true)
@AllArgsConstructor
@NoArgsConstructor
public class MenuDto extends BaseDto {
    /**
     * 菜单ID
     */
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
    @NotBlank(message = "菜单名称不能为空")
    private String menuName;

    /**
     * 菜单类型（M目录 C菜单 F按钮）
     */
    @NotBlank(message = "菜单类型不能为空")
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
    @NotBlank(message = "菜单状态不能为空")
    private String status;
}
