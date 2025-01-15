package com.jinken.openhis.modules.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jinken.openhis.modules.system.dto.DictTypeDto;
import com.jinken.openhis.modules.system.entity.DictType;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author jinken
* @description 针对表【sys_dict_type(字典类型表)】的数据库操作Service
* @createDate 2025-01-07 11:21:42
*/
public interface DictTypeService extends IService<DictType> {
    /**
     * 分页搜索字典类型
     * @param params
     * @return
     */
    Page<DictType> listForPage(DictTypeDto params);

    List<DictType> selectAllDictType();

    /**
     * 同步缓存
     */
    void dictCacheAsync();
}
