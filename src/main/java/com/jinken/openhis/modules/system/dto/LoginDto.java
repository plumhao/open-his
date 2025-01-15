package com.jinken.openhis.modules.system.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Dto ： data transition object  前端提交的数据转换成对象
 */
@Data
public class LoginDto {
    @NotBlank(message = "账号不能为空")
    private String username;
    @NotBlank(message = "密码不能为空")
    private String password;
}
