package com.lzh.openhis.modules.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzh.openhis.modules.system.entity.LoginInfo;
import com.lzh.openhis.modules.system.service.LoginInfoService;
import com.lzh.openhis.modules.system.mapper.LoginInfoMapper;
import org.springframework.stereotype.Service;

/**
* @author HP
* @description 针对表【sys_login_info(系统访问记录)】的数据库操作Service实现
* @createDate 2025-01-07 17:12:14
*/
@Service
public class LoginInfoServiceImpl extends ServiceImpl<LoginInfoMapper, LoginInfo>
    implements LoginInfoService{

}




