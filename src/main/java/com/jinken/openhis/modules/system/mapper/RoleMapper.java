package com.jinken.openhis.modules.system.mapper;

import com.jinken.openhis.modules.system.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author jinken
* @description 针对表【sys_role(角色信息表)】的数据库操作Mapper
* @createDate 2025-01-07 11:21:42
* @Entity com.jinken.openhis.modules.system.entity.Role
*/
public interface RoleMapper extends BaseMapper<Role> {
    /**
     * 根据用户id查询角色信息
     * @param userId
     * @return
     */
    List<Role> selectRolesByUserId(Long userId);
}




