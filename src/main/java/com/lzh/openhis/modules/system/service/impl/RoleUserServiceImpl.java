package com.lzh.openhis.modules.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzh.openhis.modules.system.entity.RoleUser;
import com.lzh.openhis.modules.system.service.RoleUserService;
import com.lzh.openhis.modules.system.mapper.RoleUserMapper;
import org.springframework.stereotype.Service;

/**
* @author HP
* @description 针对表【sys_role_user(用户和角色关联表)】的数据库操作Service实现
* @createDate 2025-01-07 17:12:14
*/
@Service
public class RoleUserServiceImpl extends ServiceImpl<RoleUserMapper, RoleUser>
    implements RoleUserService{

}




