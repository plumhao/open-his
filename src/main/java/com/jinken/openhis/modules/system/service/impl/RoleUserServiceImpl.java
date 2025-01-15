package com.jinken.openhis.modules.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinken.openhis.modules.system.entity.RoleUser;
import com.jinken.openhis.modules.system.service.RoleUserService;
import com.jinken.openhis.modules.system.mapper.RoleUserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
* @author jinken
* @description 针对表【sys_role_user(用户和角色关联表)】的数据库操作Service实现
* @createDate 2025-01-07 11:21:41
*/
@Service
public class RoleUserServiceImpl extends ServiceImpl<RoleUserMapper, RoleUser>
    implements RoleUserService{

    @Override
    public List<Long> getRoleIdsByUserId(Long userId) {

        LambdaQueryWrapper<RoleUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RoleUser::getUserId,userId);

        List<RoleUser> roleUsers = this.list(queryWrapper);

        //转换角色id集合
        List<Long> roleIds = roleUsers.stream().map(RoleUser::getRoleId).toList();

        return roleIds;
    }

    @Override
    @Transactional //保证数据库的本地事务
    public void saveRoleUser(Long userId, Long[] roleIds) {
        /**
         * 分配角色思路
         * 1，先删除role_user 中间表中当前用户的所有角色
         * 2，再将提交的新的角色插入进去
         */
        LambdaUpdateWrapper<RoleUser> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(RoleUser::getUserId,userId);
        this.remove(updateWrapper);

        List<RoleUser> roleUsers = new ArrayList<>();
        for (Long roleId : roleIds) {
            RoleUser roleUser = new RoleUser();
            roleUser.setRoleId(roleId);
            roleUser.setUserId(userId);

            roleUsers.add(roleUser);
        }
        //批量插入
        this.saveBatch(roleUsers);
    }


}




