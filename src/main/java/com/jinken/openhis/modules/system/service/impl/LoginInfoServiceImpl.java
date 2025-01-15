package com.jinken.openhis.modules.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinken.openhis.modules.system.dto.LoginInfoDto;
import com.jinken.openhis.modules.system.entity.LoginInfo;
import com.jinken.openhis.modules.system.service.LoginInfoService;
import com.jinken.openhis.modules.system.mapper.LoginInfoMapper;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
* @author jinken
* @description 针对表【sys_login_info(系统访问记录)】的数据库操作Service实现
* @createDate 2025-01-07 11:21:42
*/
@Service
public class LoginInfoServiceImpl extends ServiceImpl<LoginInfoMapper, LoginInfo>
    implements LoginInfoService{

    @Override
    public Page<LoginInfo> listForPage(LoginInfoDto params) {

        Page<LoginInfo> page = new Page<>(params.getPageNum(),params.getPageSize());

        LambdaQueryWrapper<LoginInfo> ew = new LambdaQueryWrapper<>();

        ew.like(StrUtil.isNotBlank(params.getUserName()),LoginInfo::getUserName,params.getUserName());
        ew.like(StrUtil.isNotBlank(params.getLoginAccount()),LoginInfo::getLoginAccount,params.getLoginAccount());
        ew.like(StrUtil.isNotBlank(params.getIpAddr()),LoginInfo::getIpAddr,params.getIpAddr());
        ew.eq(StrUtil.isNotBlank(params.getLoginStatus()),LoginInfo::getLoginStatus,params.getLoginStatus());
        ew.eq(StrUtil.isNotBlank(params.getLoginType()),LoginInfo::getLoginType,params.getLoginType());
        ew.ge(Objects.nonNull(params.getBeginTime()),LoginInfo::getLoginTime,params.getBeginTime());
        ew.le(Objects.nonNull(params.getEndTime()),LoginInfo::getLoginTime,params.getEndTime());
        //登录时间降序
        ew.orderByDesc(LoginInfo::getLoginTime);

        //执行查询
        this.page(page,ew);

        return page;
    }
}




