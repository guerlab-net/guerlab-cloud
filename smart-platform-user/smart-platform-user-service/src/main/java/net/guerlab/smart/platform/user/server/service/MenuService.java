package net.guerlab.smart.platform.user.server.service;

import net.guerlab.smart.platform.server.service.BaseService;
import net.guerlab.smart.platform.user.server.entity.Menu;

/**
 * 菜单服务
 *
 * @author guer
 */
public interface MenuService extends BaseService<Menu, Long> {

    /**
     * 获取实体类型
     *
     * @return 实体类型
     */
    @Override
    default Class<Menu> getEntityClass() {
        return Menu.class;
    }
}
