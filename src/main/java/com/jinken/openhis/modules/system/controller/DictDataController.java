package com.jinken.openhis.modules.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jinken.openhis.commons.annotation.Log;
import com.jinken.openhis.commons.enums.BusinessTypeEnum;
import com.jinken.openhis.commons.vo.AjaxResult;
import com.jinken.openhis.modules.system.dto.DictDataDto;
import com.jinken.openhis.modules.system.entity.DictData;
import com.jinken.openhis.modules.system.service.DictDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/system/dict/data")
public class DictDataController {

    @Autowired
    private DictDataService dictDataService;

    @GetMapping("/listForPage")
    public AjaxResult listForPage(DictDataDto params) {

        Page<DictData> pageResult = dictDataService.listForPage(params);

        return AjaxResult.success(pageResult.getRecords(), pageResult.getTotal());
    }


    @PostMapping("/addDictData")
    @Log(title = "新增字典数据",businessType = BusinessTypeEnum.INSERT)
    public AjaxResult addDictData(DictDataDto params) {

        dictDataService.addDictData(params);

        return AjaxResult.success();
    }

    /**
     * 根据字典的类型查询字典的列表值 ，整个项目每个模块都会调用
     *
     * @param dictType
     * @return
     */
    @GetMapping("/getDataByType/{dictType}")
    public AjaxResult getDataByType(@PathVariable String dictType) {
        List<DictData> dictDataList = dictDataService.getDataByType(dictType);
        return AjaxResult.success(dictDataList);
    }

    /**
     * 根据字典id查询字典数据
     * @param dictDataId
     * @return
     */
    @GetMapping("/getOne/{dictDataId}")
    public AjaxResult getDataByType(@PathVariable Long dictDataId) {

        return AjaxResult.success(dictDataService.getById(dictDataId));
    }


    /**
     * 修改字典数据
     * @param params
     * @return
     */
    @PutMapping("/updateDictData")
    @Log(title = "修改字典数据",businessType = BusinessTypeEnum.UPDATE)
    public AjaxResult updateDictData(DictDataDto params){

        dictDataService.updateDictData(params);

        return AjaxResult.success();
    }


    @DeleteMapping("/deleteDictDataByIds/{dictIds}")
    @Log(title = "删除字典数据",businessType = BusinessTypeEnum.DELETE)
    public AjaxResult deleteDictDataByIds(@PathVariable Long[] dictIds){

        dictDataService.removeBatchByIds(Arrays.asList(dictIds));

        return AjaxResult.success();

    }


}
