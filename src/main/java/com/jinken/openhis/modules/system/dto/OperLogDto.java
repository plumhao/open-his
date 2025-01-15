package com.jinken.openhis.modules.system.dto;


import com.jinken.openhis.commons.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Author:
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class OperLogDto extends BaseDto {
    /**
     * 模块标题
     */
    private String title;

    /**
     * 业务类型（0其它 1新增 2修改 3删除）
     */
    private String businessType;

    /**
     * 操作人员
     */
    private String operName;

    /**
     * 操作状态（0正常 1异常）
     */
    private String status;
}
