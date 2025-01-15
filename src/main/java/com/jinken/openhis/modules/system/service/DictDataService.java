package com.jinken.openhis.modules.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jinken.openhis.modules.system.dto.DictDataDto;
import com.jinken.openhis.modules.system.entity.DictData;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author jinken
* @description 针对表【sys_dict_data(字典数据表)】的数据库操作Service
* @createDate 2025-01-07 11:21:42
*/
public interface DictDataService extends IService<DictData> {
    /**
     * 分页搜索地点数据
     * @param params
     * @return
     */
    Page<DictData> listForPage(DictDataDto params);

    /**
     * 新增字典
     * @param params
     */
    void addDictData(DictDataDto params);

    /**
     * 根据字典类型查询字典数据
     * @param dictType
     * @return
     */
    List<DictData> getDataByType(String dictType);

    /**
     *
     * @param params
     */
    void updateDictData(DictDataDto params);
}
