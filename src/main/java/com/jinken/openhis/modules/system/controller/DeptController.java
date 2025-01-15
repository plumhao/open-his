package com.jinken.openhis.modules.system.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jinken.openhis.commons.vo.AjaxResult;
import com.jinken.openhis.modules.system.dto.DeptDto;
import com.jinken.openhis.modules.system.entity.Dept;
import com.jinken.openhis.modules.system.service.DeptService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/system/dept")
public class DeptController {

    @Autowired
    private DeptService deptService;

    /**
     * 分页搜索科室（部门）
     * @param params
     * @return
     */
    @GetMapping("/listDeptForPage")
    public AjaxResult listForPage(DeptDto params){

        Page<Dept> deptPage = deptService.listForPage(params);

        return AjaxResult.success(deptPage.getRecords(),deptPage.getTotal());
    }

    /**
     * 查询所有部门
     * @return
     */
    @GetMapping("/selectAllDept")
    public AjaxResult selectAllDept(){

        return AjaxResult.success(deptService.list());
    }

    /**
     * 新增科室
     * @param params
     * @return
     */
    @PostMapping("/addDept")
    public AjaxResult addDept(@Valid DeptDto params){

        deptService.addDept(params);

        return AjaxResult.success();
    }

    /**
     * 修改科室
     * @param params
     * @return
     */
    @PutMapping("/updateDept")
    public AjaxResult updateDept(@Valid DeptDto params){

        deptService.updateDept(params);

        return AjaxResult.success();
    }

    /**
     * 根据id查询部门信息
     * @param deptId
     * @return
     */
    @GetMapping("/getDeptById/{deptId}")
    public AjaxResult getDeptById(@PathVariable Long deptId){
        return AjaxResult.success(deptService.getById(deptId));
    }

    @DeleteMapping("deleteDeptByIds/{deptIds}")
    public AjaxResult deleteDeptByIds(@PathVariable Long[] deptIds){
        deptService.removeBatchByIds(Arrays.asList(deptIds));
        return AjaxResult.success();
    }
}
