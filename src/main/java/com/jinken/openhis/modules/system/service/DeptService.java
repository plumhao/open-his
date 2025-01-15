package com.jinken.openhis.modules.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jinken.openhis.modules.system.dto.DeptDto;
import com.jinken.openhis.modules.system.entity.Dept;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author jinken
* @description 针对表【sys_dept(部门/科室表)】的数据库操作Service
* @createDate 2025-01-07 11:21:42
*/
public interface DeptService extends IService<Dept> {

    Page<Dept> listForPage(DeptDto params);

    void addDept(DeptDto params);

    void updateDept(DeptDto params);
}
