/*
 * Copyright 2018-2024 guerlab.net and other contributors.
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import net.guerlab.cloud.commons.entity.IBaseEntity;
import net.guerlab.cloud.core.sequence.Sequence;
import net.guerlab.cloud.searchparams.SearchParams;
import net.guerlab.cloud.server.utils.BatchSaveUtils;
import net.guerlab.commons.collection.CollectionUtil;

/**
 * 基本ORM服务实现.
 *
 * @param <E> 数据类型
 * @param <M> Mapper类型
 * @param <Q> 搜索参数类型
 * @author guer
 */
@SuppressWarnings({"unused", "EmptyMethod"})
@Slf4j
@Validated
public abstract class BaseOrmServiceImpl<E extends IBaseEntity, M extends BaseMapper<E>, Q extends SearchParams>
		extends BaseOrmFindServiceImpl<E, M, Q>
		implements BaseOrmService<E, Q> {

	private static final Log ORM_LOGGER = LogFactory.getLog(BaseOrmServiceImpl.class);

	protected BaseOrmServiceImpl(Sequence sequence, M baseMapper) {
		super(sequence, baseMapper);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public E insert(E entity) {
		entity.id(null);
		insertBefore(entity);
		getBaseMapper().insert(entity);
		insertAfter(entity);
		return entity;
	}

	/**
	 * 添加前.
	 *
	 * @param entity 实体
	 */
	protected void insertBefore(E entity) {
		/* 默认空实现 */
	}

	/**
	 * 添加后.
	 *
	 * @param entity 实体
	 */
	protected void insertAfter(E entity) {
		/* 默认空实现 */
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<E> batchInsert(Collection<? extends E> collection, boolean ignoreBeforeCheckException) {
		List<E> list = BatchSaveUtils.filter(collection, item -> batchSaveBefore(item, ignoreBeforeCheckException));

		if (CollectionUtil.isNotEmpty(list)) {
			saveBatch(list, DEFAULT_BATCH_SIZE);
		}

		return list;
	}

	/**
	 * 保存检查.
	 *
	 * @param entity                     实体
	 * @param ignoreBeforeCheckException 是否忽略前置检查异常
	 * @return 如果返回null则不保存该对象，否则保存该对象
	 */
	@Nullable
	protected E batchSaveBefore(E entity, boolean ignoreBeforeCheckException) {
		try {
			insertBefore(entity);
			return entity;
		}
		catch (Exception e) {
			log.debug(e.getMessage(), e);
			if (ignoreBeforeCheckException) {
				return null;
			}
			throw e;
		}
	}

	/**
	 * 批量保存.
	 *
	 * @param entities  实体列表
	 * @param batchSize 单次操作数量
	 */
	@SuppressWarnings("SameParameterValue")
	protected final void saveBatch(Collection<E> entities, int batchSize) {
		String sqlStatement = SqlHelper.getSqlStatement(this.mapperClass, SqlMethod.INSERT_ONE);
		this.executeBatch(entities, batchSize, (sqlSession, entity) -> sqlSession.insert(sqlStatement, entity));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean updateById(E entity) {
		updateBefore(entity);
		int result = getBaseMapper().updateById(entity);
		updateAfter(entity);
		return result > 0;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean update(E entity, Q searchParams) {
		return getBaseMapper().update(entity, getQueryWrapper(searchParams)) > 0;
	}

	/**
	 * 更新前.
	 *
	 * @param entity 实体
	 */
	protected void updateBefore(E entity) {
		/* 默认空实现 */
	}

	/**
	 * 更新后.
	 *
	 * @param entity 实体
	 */
	protected void updateAfter(E entity) {
		/* 默认空实现 */
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<E> batchUpdateById(Collection<? extends E> collection, boolean ignoreBeforeCheckException) {
		List<E> list = BatchSaveUtils.filter(collection, item -> batchUpdateBefore(item, ignoreBeforeCheckException));

		if (CollectionUtil.isNotEmpty(list)) {
			updateBatch(list, DEFAULT_BATCH_SIZE);
		}

		return list;
	}

	/**
	 * 更新检查.
	 *
	 * @param entity                     实体
	 * @param ignoreBeforeCheckException 是否忽略前置检查异常
	 * @return 如果返回null则不保存该对象，否则保存该对象
	 */
	@Nullable
	protected E batchUpdateBefore(E entity, boolean ignoreBeforeCheckException) {
		try {
			updateBefore(entity);
			return entity;
		}
		catch (Exception e) {
			log.debug(e.getMessage(), e);
			if (ignoreBeforeCheckException) {
				return null;
			}
			throw e;
		}
	}

	/**
	 * 批量更新.
	 *
	 * @param entities  实体列表
	 * @param batchSize 单次操作数量
	 */
	@SuppressWarnings("SameParameterValue")
	protected final void updateBatch(Collection<E> entities, int batchSize) {
		String sqlStatement = SqlHelper.getSqlStatement(this.mapperClass, SqlMethod.UPDATE_BY_ID);
		this.executeBatch(entities, batchSize, (sqlSession, entity) -> {
			MapperMethod.ParamMap<E> param = new MapperMethod.ParamMap<>();
			param.put(Constants.ENTITY, entity);
			sqlSession.update(sqlStatement, param);
		});
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public E saveOrUpdate(E entity) {
		Long id = entity.id();
		if (id == null) {
			return insert(entity);
		}

		updateById(entity);
		return selectById(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<E> batchSaveOrUpdate(Collection<? extends E> list, boolean ignoreBeforeCheckException) {
		list = list.stream().filter(Objects::nonNull).toList();
		if (list.isEmpty()) {
			return Collections.emptyList();
		}

		List<E> result = new ArrayList<>(list.size());
		Map<Boolean, List<E>> groupMap = list.stream().collect(Collectors.groupingBy(entity -> entity.id() == null));
		List<E> needAdds = groupMap.getOrDefault(true, Collections.emptyList());
		List<E> needUpdates = groupMap.getOrDefault(false, Collections.emptyList());

		if (!needAdds.isEmpty()) {
			result.addAll(batchInsert(needAdds, ignoreBeforeCheckException));
		}
		if (!needUpdates.isEmpty()) {
			result.addAll(batchUpdateById(needUpdates, ignoreBeforeCheckException));
		}

		return result;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delete(Q searchParams) {
		deleteBefore(searchParams);
		QueryWrapper<E> queryWrapper = getQueryWrapper(searchParams);
		getBaseMapper().delete(queryWrapper);
		deleteAfter(searchParams);
	}

	/**
	 * 删除前.
	 *
	 * @param searchParams 搜索参数
	 */
	protected void deleteBefore(Q searchParams) {
		/* 默认空实现 */
	}

	/**
	 * 删除后.
	 *
	 * @param searchParams 搜索参数
	 */
	protected void deleteAfter(Q searchParams) {
		/* 默认空实现 */
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteById(Long id) {
		deleteByIdBefore(id);
		getBaseMapper().deleteById(id);
		deleteByIdAfter(id);
	}

	/**
	 * 删除前.
	 *
	 * @param id id
	 */
	protected void deleteByIdBefore(Long id) {
		/* 默认空实现 */
	}

	/**
	 * 删除后.
	 *
	 * @param id id
	 */
	protected void deleteByIdAfter(Long id) {
		/* 默认空实现 */
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delete(LambdaQueryWrapper<E> queryWrapper) {
		getBaseMapper().delete(queryWrapper);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delete(QueryWrapper<E> queryWrapper) {
		getBaseMapper().delete(queryWrapper);
	}

	/**
	 * 获取当前mapper对象类型.
	 *
	 * @return mapper对象类型
	 */
	@SuppressWarnings("unchecked")
	protected Class<E> currentMapperClass() {
		return (Class<E>) ReflectionKit.getSuperClassGenericType(this.getClass(), BaseOrmServiceImpl.class, 1);
	}

	/**
	 * 获取当前模型对象类型.
	 *
	 * @return 模型对象类型
	 */
	@SuppressWarnings("unchecked")
	protected Class<E> currentModelClass() {
		return (Class<E>) ReflectionKit.getSuperClassGenericType(this.getClass(), BaseOrmServiceImpl.class, 0);
	}
}
