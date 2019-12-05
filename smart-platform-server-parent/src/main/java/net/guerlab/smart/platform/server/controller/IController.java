package net.guerlab.smart.platform.server.controller;

/**
 * 控制器接口
 *
 * @param <E>
 *         实体对象类型
 * @param <S>
 *         服务接口类型
 * @param <PK>
 *         实体主键类型
 * @author guer
 */
public interface IController<E, S, PK> {

    /**
     * 获取服务对象
     *
     * @return 服务对象
     */
    S getService();

    /**
     * 创建新实体
     *
     * @return 实体
     */
    E newEntity();

    /**
     * 查询对象
     *
     * @param id
     *         主键id
     * @return 对象
     */
    E findOne0(PK id);
}
