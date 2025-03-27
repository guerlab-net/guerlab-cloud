/*
 * Copyright 2018-2025 guerlab.net and other contributors.
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.gnu.org/licenses/lgpl-3.0.html
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.guerlab.cloud.server.service.orm;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import jakarta.annotation.Nullable;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import org.springframework.validation.annotation.Validated;

import net.guerlab.cloud.commons.entity.IBaseEntity;
import net.guerlab.cloud.core.result.Pageable;
import net.guerlab.cloud.core.sequence.Sequence;
import net.guerlab.cloud.searchparams.SearchParams;
import net.guerlab.cloud.server.utils.PageUtils;

/**
 * 基本ORM查询服务实现.
 *
 * @param <E> 数据类型
 * @param <M> Mapper类型
 * @param <Q> 搜索参数类型
 * @author guer
 */
@SuppressWarnings({"unused", "EmptyMethod"})
@Slf4j
@Validated
public abstract class BaseOrmFindServiceImpl<E extends IBaseEntity, M extends BaseMapper<E>, Q extends SearchParams>
		implements BaseOrmFindService<E, Q> {

	/**
	 * 默认单次操作数量.
	 */
	protected static final int DEFAULT_BATCH_SIZE = 1000;

	private static final Log ORM_LOGGER = LogFactory.getLog(BaseOrmFindServiceImpl.class);

	/**
	 * 实体类型.
	 */
	protected final Class<E> entityClass = this.currentModelClass();

	/**
	 * mapper类型.
	 */
	protected final Class<E> mapperClass = this.currentMapperClass();

	/**
	 * 序列.
	 */
	protected final Sequence sequence;

	/**
	 * mapper.
	 */
	protected final M baseMapper;

	@Resource
	private SqlSessionFactory sqlSessionFactory;

	/**
	 * 创建基本ORM查询服务实现.
	 *
	 * @param sequence   序列
	 * @param baseMapper mapper
	 */
	protected BaseOrmFindServiceImpl(Sequence sequence, M baseMapper) {
		this.sequence = sequence;
		this.baseMapper = baseMapper;
	}

	/**
	 * 获取mapper.
	 *
	 * @return mapper
	 */
	public final M getBaseMapper() {
		return this.baseMapper;
	}

	@Nullable
	@Override
	public E selectOne(E entity) {
		QueryWrapper<E> queryWrapper = getQueryWrapper();
		queryWrapper.setEntity(entity);
		E result = getBaseMapper().selectOne(queryWrapper);
		if (result != null) {
			afterSelect(Collections.singleton(result), null);
		}
		return result;
	}

	@Nullable
	@Override
	public E selectOne(Q searchParams) {
		E result = getBaseMapper().selectOne(getQueryWrapperWithSelectMethod(searchParams));
		if (result != null) {
			afterSelect(Collections.singleton(result), searchParams);
		}
		return result;
	}

	@Nullable
	@Override
	public E selectById(Long id) {
		E result = getBaseMapper().selectById(id);
		if (result != null) {
			afterSelect(Collections.singleton(result), null);
		}
		return result;
	}

	@Override
	public List<E> selectByIds(List<Long> ids) {
		ids = ids.stream().filter(Objects::nonNull).distinct().toList();
		if (ids.isEmpty()) {
			return Collections.emptyList();
		}
		List<E> list = getBaseMapper().selectBatchIds(ids);
		if (!list.isEmpty()) {
			afterSelect(list, null);
		}
		return list;
	}

	@Override
	public List<E> selectList(Q searchParams) {
		if (!beforeSelect(searchParams)) {
			return Collections.emptyList();
		}
		QueryWrapper<E> queryWrapper = getQueryWrapperWithSelectMethod(searchParams);
		List<E> list = getBaseMapper().selectList(queryWrapper);
		if (!list.isEmpty()) {
			afterSelect(list, searchParams);
		}
		return list;
	}

	@Override
	public Pageable<E> selectPage(Q searchParams, int pageId, int pageSize) {
		if (!beforeSelect(searchParams)) {
			return Pageable.empty();
		}
		Pageable<E> result = PageUtils.selectPage(this, searchParams, pageId, pageSize, getBaseMapper());
		if (!result.getList().isEmpty()) {
			afterSelect(result.getList(), searchParams);
		}
		return result;
	}

	/**
	 * 搜索前置操作.
	 *
	 * @param searchParams 搜索参数
	 * @return 是否允许搜索
	 */
	@SuppressWarnings({"BooleanMethodIsAlwaysInverted", "SameReturnValue"})
	protected boolean beforeSelect(Q searchParams) {
		return true;
	}

	/**
	 * 搜索后置操作.
	 *
	 * @param items        对象列表
	 * @param searchParams 搜索参数
	 */
	protected void afterSelect(Collection<E> items, @Nullable Q searchParams) {

	}

	@Override
	public long selectCount(Q searchParams) {
		Long result = getBaseMapper().selectCount(getQueryWrapperWithSelectMethod(searchParams));
		return result == null ? 0L : result;
	}

	/**
	 * 批量执行.
	 *
	 * @param list      实体列表
	 * @param batchSize 单次执行数量
	 * @param consumer  操作内容
	 */
	protected void executeBatch(Collection<E> list, int batchSize, BiConsumer<SqlSession, E> consumer) {
		SqlHelper.executeBatch(sqlSessionFactory, ORM_LOGGER, list, batchSize, consumer);
	}

	/**
	 * 获取当前mapper对象类型.
	 *
	 * @return mapper对象类型
	 */
	@SuppressWarnings("unchecked")
	protected Class<E> currentMapperClass() {
		return (Class<E>) ReflectionKit.getSuperClassGenericType(this.getClass(), BaseOrmFindServiceImpl.class, 1);
	}

	/**
	 * 获取当前模型对象类型.
	 *
	 * @return 模型对象类型
	 */
	@SuppressWarnings("unchecked")
	protected Class<E> currentModelClass() {
		return (Class<E>) ReflectionKit.getSuperClassGenericType(this.getClass(), BaseOrmFindServiceImpl.class, 0);
	}
}
