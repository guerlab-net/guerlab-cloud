package net.guerlab.smart.platform.server.service;

import net.guerlab.spring.searchparams.AbstractSearchParams;

import java.io.Serializable;

/**
 * 基本服务接口
 *
 * @param <T>
 *         数据类型
 * @param <PK>
 *         主键类型
 * @param <SP>
 *         搜索参数类型
 * @author guer
 */
public interface BaseService<T, PK extends Serializable, SP extends AbstractSearchParams>
        extends BaseFindService<T, PK, SP>, BaseSaveService<T, SP>, BaseUpdateService<T, SP>, BaseDeleteService<T, PK, SP>, QueryWrapperGetter<T, SP> {

}
