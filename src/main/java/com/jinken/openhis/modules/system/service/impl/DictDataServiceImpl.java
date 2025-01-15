package com.jinken.openhis.modules.system.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinken.openhis.commons.constant.Constants;
import com.jinken.openhis.commons.utils.UserUtils;
import com.jinken.openhis.modules.system.dto.DictDataDto;
import com.jinken.openhis.modules.system.entity.DictData;
import com.jinken.openhis.modules.system.entity.User;
import com.jinken.openhis.modules.system.service.DictDataService;
import com.jinken.openhis.modules.system.mapper.DictDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author jinken
 * @description 针对表【sys_dict_data(字典数据表)】的数据库操作Service实现
 * @createDate 2025-01-07 11:21:42
 */
@Service
public class DictDataServiceImpl extends ServiceImpl<DictDataMapper, DictData>
        implements DictDataService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Page<DictData> listForPage(DictDataDto params) {

        Page<DictData> page = new Page<>(params.getPageNum(), params.getPageSize());

        LambdaQueryWrapper<DictData> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StrUtil.isNotBlank(params.getDictLabel()), DictData::getDictLabel, params.getDictLabel());
        queryWrapper.eq(StrUtil.isNotBlank(params.getStatus()), DictData::getStatus, params.getStatus());
        queryWrapper.eq(StrUtil.isNotBlank(params.getDictType()), DictData::getDictType, params.getDictType());

        this.page(page, queryWrapper);

        return page;
    }

    @Override
    public void addDictData(DictDataDto params) {


        DictData dictData = new DictData();

        //将参数对象的数据拷贝到DictData对象
        BeanUtil.copyProperties(params,dictData);

        //设置创建时间
        dictData.setCreateTime(new Date());

        //获取当前登录用户
        User user = UserUtils.getLoginUser();

        //设置创建者
        dictData.setCreateBy(user.getUserName());

        this.save(dictData);
    }

    @Override
    public void updateDictData(DictDataDto params) {


        DictData dictData = new DictData();

        BeanUtil.copyProperties(params,dictData);

        dictData.setUpdateTime(new Date());

        //获取当前登录用户
        User user = UserUtils.getLoginUser();

        dictData.setUpdateBy(user.getUserName());


        this.updateById(dictData);
    }

    @Override
    public List<DictData> getDataByType(String dictType) {
//        LambdaQueryWrapper<DictData> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(DictData::getDictType,dictType);
//
//        return this.list(queryWrapper);

        //从Redis查询数据
        List<DictData>  dictDataList= (List<DictData>) redisTemplate.boundValueOps(Constants.DICT_REDIS_PROFIX + dictType).get();
        return dictDataList;

    }


}




