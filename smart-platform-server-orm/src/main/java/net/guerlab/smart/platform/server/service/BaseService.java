package net.guerlab.smart.platform.server.service;

/**
 * 基本服务接口
 *
 * @param <T>
 *         数据类型
 * @param <PK>
 *         主键类型
 * @author guer
 */
public interface BaseService<T, PK>
        extends BaseFindService<T, PK>, BaseSaveService<T>, BaseUpdateService<T>, BaseDeleteService<T, PK>, ExampleGetter<T> {

}
