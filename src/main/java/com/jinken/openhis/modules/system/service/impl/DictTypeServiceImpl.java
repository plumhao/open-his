package com.jinken.openhis.modules.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinken.openhis.commons.constant.Constants;
import com.jinken.openhis.modules.system.dto.DictTypeDto;
import com.jinken.openhis.modules.system.entity.DictData;
import com.jinken.openhis.modules.system.entity.DictType;
import com.jinken.openhis.modules.system.mapper.DictDataMapper;
import com.jinken.openhis.modules.system.service.DictTypeService;
import com.jinken.openhis.modules.system.mapper.DictTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
* @author jinken
* @description 针对表【sys_dict_type(字典类型表)】的数据库操作Service实现
* @createDate 2025-01-07 11:21:42
*/
@Service
public class DictTypeServiceImpl extends ServiceImpl<DictTypeMapper, DictType>
    implements DictTypeService{

    @Autowired
    private RedisTemplate redisTemplate;


    @Autowired
    private DictDataMapper dictDataMapper;

    @Override
    public Page<DictType> listForPage(DictTypeDto params) {

        Page<DictType> page = new Page<>(params.getPageNum(), params.getPageSize());

        //创建条件对象
        LambdaQueryWrapper<DictType> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.like(StrUtil.isNotBlank(params.getDictName()),DictType::getDictName,params.getDictName());
        queryWrapper.like(StrUtil.isNotBlank(params.getDictType()),DictType::getDictType,params.getDictType());
        queryWrapper.eq(StrUtil.isNotBlank(params.getStatus()),DictType::getStatus,params.getStatus());

        queryWrapper.ge(Objects.nonNull(params.getBeginTime()),DictType::getCreateTime,params.getBeginTime());
        queryWrapper.le(Objects.nonNull(params.getEndTime()),DictType::getCreateTime,params.getEndTime());

        //分页查询
        this.page(page,queryWrapper);

        return page;
    }

    @Override
    public List<DictType> selectAllDictType() {
        LambdaQueryWrapper<DictType> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DictType::getStatus, Constants.STATUS_TRUE);
        return list(queryWrapper);
    }

    /**
     * 同步缓存的做法
     * 1，查询出所有可用的字典类型数据
     * 2，再根据字典的类型查询字典数据
     * 3，把字典数据生成json存到redis
     * 设计key
     * dict:dictType
     * 如dict:sys_user_sex --->[{},{},{}]
     *
     */
    @Override
    public void dictCacheAsync() {
        //1，查询出所有可用的字典类型数据
        List<DictType> dictTypes = this.selectAllDictType();
        for (DictType dictType : dictTypes) {
            //2，再根据字典的类型查询字典数据
            LambdaQueryWrapper<DictData> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(DictData::getStatus,Constants.STATUS_TRUE);
            queryWrapper.eq(DictData::getDictType,dictType.getDictType());
            List<DictData> dictData = dictDataMapper.selectList(queryWrapper);


            //方式一：没个字典类型一个key，key存储字典数据
//            //创建RedisKey dict:sys_user_sex
//            String redisKey = Constants.DICT_REDIS_PROFIX+dictType.getDictType();
//            //将数据存储到redis
//            redisTemplate.boundValueOps(redisKey).set(dictData);
            //方式二：使用hash类型，一个key，每个类型做hash的key
            redisTemplate.boundHashOps("dict_cache").put(dictType.getDictType(),dictData);
        }

    }

}




