package com.lzh.openhis.modules.system.mapper;

import com.lzh.openhis.modules.system.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @author HP
 * @description 针对表【sys_menu(菜单权限表)】的数据库操作Mapper
 * @createDate 2025-01-07 17:12:14
 * @Entity com.lzh.openhis.modules.system.entity.Menu
 */
public interface MenuMapper extends BaseMapper<Menu> {
    /**
     * 查询指定用户的菜单
     *
     * @param userId
     * @return
     */
    List<Menu> selectMenusByUserId(Long userId);
}




