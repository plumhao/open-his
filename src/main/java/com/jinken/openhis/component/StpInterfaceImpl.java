package com.jinken.openhis.component;

import cn.dev33.satoken.stp.StpInterface;
import cn.hutool.core.util.StrUtil;
import com.jinken.openhis.commons.constant.Constants;
import com.jinken.openhis.commons.utils.UserUtils;
import com.jinken.openhis.modules.system.entity.Menu;
import com.jinken.openhis.modules.system.entity.User;
import com.jinken.openhis.modules.system.mapper.MenuMapper;
import com.jinken.openhis.modules.system.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 自定义权限加载接口实现类
 */
@Component    // 保证此类被 SpringBoot 扫描，完成 Sa-Token 的自定义权限验证扩展
public class StpInterfaceImpl implements StpInterface {


    @Autowired
    private MenuMapper menuMapper;


    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {

        User user = UserUtils.getLoginUser();

        Long userId = user.getUserId();
        if (user.getUserType().equals(Constants.USER_ADMIN)) {
            userId = null;
        }

        //获取所有菜单
        List<Menu> menus = menuMapper.selectMenusByUserId(userId);

        //获取权限表达式
        List<String> preCodes = menus.stream().map(Menu::getPercode).filter(StrUtil::isNotBlank).toList();
        // ["user:add","user:list"]
        System.out.println("preCodes = " + preCodes);
        return preCodes;
    }

    @Override
    public List<String> getRoleList(Object o, String s) {
        return null;
    }
}
