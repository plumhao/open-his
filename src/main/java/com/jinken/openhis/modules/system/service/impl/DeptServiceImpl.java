package com.jinken.openhis.modules.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinken.openhis.commons.utils.UserUtils;
import com.jinken.openhis.modules.system.dto.DeptDto;
import com.jinken.openhis.modules.system.entity.Dept;
import com.jinken.openhis.modules.system.entity.DictType;
import com.jinken.openhis.modules.system.entity.User;
import com.jinken.openhis.modules.system.service.DeptService;
import com.jinken.openhis.modules.system.mapper.DeptMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

/**
* @author jinken
* @description 针对表【sys_dept(部门/科室表)】的数据库操作Service实现
* @createDate 2025-01-07 11:21:42
*/
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept>
    implements DeptService{

    @Override
    public Page<Dept> listForPage(DeptDto params) {

        Page<Dept> deptPage = new Page<>(params.getPageNum(), params.getPageSize());

        LambdaQueryWrapper<Dept> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.like(StrUtil.isNotBlank(params.getDeptName()),Dept::getDeptName,params.getDeptName());
        queryWrapper.eq(StrUtil.isNotBlank(params.getStatus()),Dept::getStatus,params.getStatus());

        queryWrapper.ge(Objects.nonNull(params.getBeginTime()),Dept::getCreateTime,params.getBeginTime());
        queryWrapper.le(Objects.nonNull(params.getEndTime()),Dept::getCreateTime,params.getEndTime());

        //按照显示顺序升序
        queryWrapper.orderByAsc(Dept::getOrderNum);

        this.page(deptPage,queryWrapper);

        return deptPage;
    }

    @Override
    public void addDept(DeptDto params) {

        //创建科室
        Dept dept = new Dept();
        BeanUtil.copyProperties(params,dept);

        User user = UserUtils.getLoginUser();
        dept.setCreateBy(user.getUserName());
        dept.setCreateTime(new Date());


        this.save(dept);
    }

    @Override
    public void updateDept(DeptDto params) {

        //创建科室
        Dept dept = new Dept();
        BeanUtil.copyProperties(params,dept);

        User user = UserUtils.getLoginUser();
        dept.setUpdateBy(user.getUserName());
        dept.setUpdateTime(new Date());

        this.updateById(dept);

    }
}




