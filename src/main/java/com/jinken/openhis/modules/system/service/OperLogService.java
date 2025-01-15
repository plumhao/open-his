package com.jinken.openhis.modules.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jinken.openhis.modules.system.dto.OperLogDto;
import com.jinken.openhis.modules.system.entity.OperLog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author jinken
* @description 针对表【sys_oper_log(操作日志记录)】的数据库操作Service
* @createDate 2025-01-07 11:21:42
*/
public interface OperLogService extends IService<OperLog> {
    /**
     * 分页搜索操作日志
     * @param params
     * @return
     */
    Page<OperLog> listForPage(OperLogDto params);
}
