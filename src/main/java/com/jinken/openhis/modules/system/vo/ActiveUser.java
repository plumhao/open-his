package com.jinken.openhis.modules.system.vo;

import com.jinken.openhis.modules.system.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActiveUser implements Serializable {
    private User user;
    private List<String> roles;//角色
    private List<String> permissions;//权限
}
