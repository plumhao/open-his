package com.jinken.openhis.modules.system.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jinken.openhis.commons.vo.AjaxResult;
import com.jinken.openhis.modules.system.dto.OperLogDto;
import com.jinken.openhis.modules.system.entity.OperLog;
import com.jinken.openhis.modules.system.service.OperLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;


@RestController
@RequestMapping("/system/operLog")
public class OperLogController {

    @Autowired
    private OperLogService operLogService;

    @GetMapping("/listForPage")
    public AjaxResult listForPage(OperLogDto params){

        Page<OperLog> page = operLogService.listForPage(params);

        return AjaxResult.success(page.getRecords(), page.getTotal());
    }

    @DeleteMapping("/deleteOperLogByIds/{operIds}")
    public AjaxResult deleteOperLogByIds(@PathVariable Long[] operIds){

        operLogService.removeBatchByIds(Arrays.asList(operIds));

        return AjaxResult.success();
    }
    @DeleteMapping("/clearAllOperLog")
    public AjaxResult clearAllOperLog(){

        operLogService.remove(null);

        return AjaxResult.success();
    }

}
