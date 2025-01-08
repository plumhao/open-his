package com.lzh.openhis.modules.system.mapper;

import com.lzh.openhis.modules.system.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @author HP
 * @description 针对表【sys_role(角色信息表)】的数据库操作Mapper
 * @createDate 2025-01-07 17:12:14
 * @Entity com.lzh.openhis.modules.system.entity.Role
 */
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 根据用户id查询角色信息
     *
     * @param userId
     * @return
     */
    List<Role> selectRolesByUserId(Long userId);
}




