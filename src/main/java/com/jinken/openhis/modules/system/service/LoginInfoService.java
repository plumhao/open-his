package com.jinken.openhis.modules.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jinken.openhis.modules.system.dto.LoginInfoDto;
import com.jinken.openhis.modules.system.entity.LoginInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author jinken
* @description 针对表【sys_login_info(系统访问记录)】的数据库操作Service
* @createDate 2025-01-07 11:21:42
*/
public interface LoginInfoService extends IService<LoginInfo> {
    /**
     * 分页搜索登录信息
     * @param params
     * @return
     */
    Page<LoginInfo> listForPage(LoginInfoDto params);
}
