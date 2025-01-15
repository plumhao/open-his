package com.jinken.openhis.modules.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jinken.openhis.modules.system.dto.RoleDto;
import com.jinken.openhis.modules.system.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author jinken
* @description 针对表【sys_role(角色信息表)】的数据库操作Service
* @createDate 2025-01-07 11:21:42
*/
public interface RoleService extends IService<Role> {

    List<Role> selectAllRole();

    Page<Role> listForPage(RoleDto params);

    void addRole(RoleDto params);

    void updateRole(RoleDto params);

    void deleteDeptByIds(Long[] roleIds);
}
