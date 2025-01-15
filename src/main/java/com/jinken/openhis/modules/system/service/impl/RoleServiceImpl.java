package com.jinken.openhis.modules.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinken.openhis.commons.constant.Constants;
import com.jinken.openhis.commons.enums.ErrorCodeAndMessageEnum;
import com.jinken.openhis.commons.exception.BusinessException;
import com.jinken.openhis.commons.utils.UserUtils;
import com.jinken.openhis.modules.system.dto.RoleDto;
import com.jinken.openhis.modules.system.entity.Role;
import com.jinken.openhis.modules.system.entity.RoleMenu;
import com.jinken.openhis.modules.system.entity.RoleUser;
import com.jinken.openhis.modules.system.entity.User;
import com.jinken.openhis.modules.system.mapper.RoleMenuMapper;
import com.jinken.openhis.modules.system.mapper.RoleUserMapper;
import com.jinken.openhis.modules.system.service.RoleService;
import com.jinken.openhis.modules.system.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;


/**
 * @author jinken
 * @description 针对表【sys_role(角色信息表)】的数据库操作Service实现
 * @createDate 2025-01-07 11:21:42
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
        implements RoleService {

    @Autowired
    private RoleUserMapper roleUserMapper;

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Override
    public List<Role> selectAllRole() {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Role::getStatus, Constants.STATUS_TRUE);

        return this.list(queryWrapper);
    }

    @Override
    public Page<Role> listForPage(RoleDto params) {
        Page<Role> rolePage = new Page<>(params.getPageNum(), params.getPageSize());

        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StrUtil.isNotBlank(params.getRoleName()), Role::getRoleName, params.getRoleName());
        queryWrapper.like(StrUtil.isNotBlank(params.getRoleCode()), Role::getRoleCode, params.getRoleCode());
        queryWrapper.eq(StrUtil.isNotBlank(params.getStatus()), Role::getStatus, params.getStatus());
        queryWrapper.ge(Objects.nonNull(params.getBeginTime()), Role::getCreateTime, params.getBeginTime());
        queryWrapper.le(Objects.nonNull(params.getEndTime()), Role::getCreateTime, params.getEndTime());

        this.page(rolePage, queryWrapper);

        return rolePage;
    }

    @Override
    public void addRole(RoleDto params) {

        Role role = new Role();
        BeanUtil.copyProperties(params, role);
        User user = UserUtils.getLoginUser();
        role.setCreateBy(user.getUserName());
        role.setCreateTime(new Date());

        this.save(role);


    }

    @Override
    public void updateRole(RoleDto params) {
        Role role = new Role();
        BeanUtil.copyProperties(params, role);
        User user = UserUtils.getLoginUser();
        role.setUpdateBy(user.getUserName());
        role.setUpdateTime(new Date());

        this.updateById(role);
    }

    @Override
    public void deleteDeptByIds(Long[] roleIds) {
        /**
         * 1. 用户依赖角色，删除角色之前判断中间表（sys_role_user）是否还关联有用户
         *
         *
         */
        LambdaQueryWrapper<RoleUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(RoleUser::getRoleId, roleIds);
        Long userCount = roleUserMapper.selectCount(queryWrapper);
        //说明当前要删除的角色还关联有用户
        if (userCount > 0) {
            throw new BusinessException(ErrorCodeAndMessageEnum.ROLE_HAS_USER_ERROR);
        }
        //批量删除角色
        this.removeBatchByIds(Arrays.asList(roleIds));

        /**
         * 2. 角色分类了菜单（权限），删除角色直接删除中间表（sys_role_menu）的所有角对应的菜单（权限）
         */

        LambdaUpdateWrapper<RoleMenu> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.in(RoleMenu::getRoleId, roleIds);
        roleMenuMapper.delete(updateWrapper);


    }
}




