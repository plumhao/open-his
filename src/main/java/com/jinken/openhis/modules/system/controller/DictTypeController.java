package com.jinken.openhis.modules.system.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jinken.openhis.commons.vo.AjaxResult;
import com.jinken.openhis.modules.system.dto.DictDataDto;
import com.jinken.openhis.modules.system.dto.DictTypeDto;
import com.jinken.openhis.modules.system.entity.DictType;
import com.jinken.openhis.modules.system.service.DictTypeService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/dict/type") //模块
public class DictTypeController {

    @Autowired
    private DictTypeService dictTypeService;

    /**
     * 分页搜索字典类型
     *
     * @param params
     * @return
     */
    @GetMapping("/listForPage")
    public AjaxResult listForPage(DictTypeDto params) {

        Page<DictType> pageResult = dictTypeService.listForPage(params);

        return AjaxResult.success(pageResult.getRecords(), pageResult.getTotal());
    }

    /**
     * 查询所有的字典类型
     *
     * @return
     */
    @GetMapping("/selectAllDictType")
    public AjaxResult selectAllDictType() {

        List<DictType> dictTypes = dictTypeService.selectAllDictType();

        return AjaxResult.success(dictTypes);
    }

    /**
     * 根据字典类型id查询出字典类型
     * @param typeId
     * @return
     */
    @GetMapping("/getOne/{typeId}")
    public AjaxResult getOne(@PathVariable @Validated @NotNull(message = "数据字典类型id不能为空") Integer typeId) {

        return AjaxResult.success(dictTypeService.getById(typeId));
    }


    /**
     * 同步MySQL数据到缓存（Redis）
     * @return
     */
    @GetMapping("/dictCacheAsync")
    public AjaxResult dictCacheAsync(){
        dictTypeService.dictCacheAsync();
        return AjaxResult.success();
    }



}
