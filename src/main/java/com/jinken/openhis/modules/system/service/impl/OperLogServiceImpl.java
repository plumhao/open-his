package com.jinken.openhis.modules.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinken.openhis.modules.system.dto.OperLogDto;
import com.jinken.openhis.modules.system.entity.LoginInfo;
import com.jinken.openhis.modules.system.entity.OperLog;
import com.jinken.openhis.modules.system.service.OperLogService;
import com.jinken.openhis.modules.system.mapper.OperLogMapper;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
* @author jinken
* @description 针对表【sys_oper_log(操作日志记录)】的数据库操作Service实现
* @createDate 2025-01-07 11:21:42
*/
@Service
public class OperLogServiceImpl extends ServiceImpl<OperLogMapper, OperLog>
    implements OperLogService{

    @Override
    public Page<OperLog> listForPage(OperLogDto params) {
        Page<OperLog> operLogPage = new Page<>(params.getPageNum(), params.getPageSize());

        //创建条件对象
        LambdaQueryWrapper<OperLog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StrUtil.isNotBlank(params.getTitle()),OperLog::getTitle,params.getTitle());
        queryWrapper.like(StrUtil.isNotBlank(params.getOperName()),OperLog::getOperName,params.getOperName());
        queryWrapper.eq(StrUtil.isNotBlank(params.getBusinessType()),OperLog::getBusinessType,params.getBusinessType());
        queryWrapper.eq(StrUtil.isNotBlank(params.getStatus()),OperLog::getStatus,params.getStatus());
        queryWrapper.ge(Objects.nonNull(params.getBeginTime()), OperLog::getOperTime,params.getBeginTime());
        queryWrapper.le(Objects.nonNull(params.getEndTime()), OperLog::getOperTime,params.getEndTime());
        //登录时间降序
        queryWrapper.orderByDesc(OperLog::getOperTime);

        //执行查询
        this.page(operLogPage,queryWrapper);

        return operLogPage;
    }
}




