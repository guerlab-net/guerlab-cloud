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

package net.guerlab.mybatis.typehandlers;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import net.guerlab.cloud.core.util.SpringUtils;
import net.guerlab.commons.exception.ApplicationException;

/**
 * 抽象类型处理.
 *
 * @author guer
 */
@SuppressWarnings("WeakerAccess")
public abstract class AbstractTypeHandler<E> extends BaseTypeHandler<E> {

	@Override
	public final void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType)
			throws SQLException {
		ps.setString(i, this.toJson(parameter));
	}

	@Override
	@Nullable
	public final E getNullableResult(ResultSet rs, String columnName) throws SQLException {
		return this.toObject(rs.getString(columnName));
	}

	@Override
	@Nullable
	public final E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		return this.toObject(rs.getString(columnIndex));
	}

	@Override
	@Nullable
	public final E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		return this.toObject(cs.getString(columnIndex));
	}

	private ObjectMapper objectMapper() {
		return SpringUtils.getContext().getBean(ObjectMapper.class);
	}

	private String toJson(E object) {
		try {
			return objectMapper().writeValueAsString(object);
		}
		catch (Exception e) {
			throw new ApplicationException(e);
		}
	}

	@Nullable
	private E toObject(String content) {
		String data = StringUtils.trimToNull(content);
		if (data == null) {
			return null;
		}

		try {
			return objectMapper().readValue(data, getTypeReference());
		}
		catch (Exception e) {
			throw new ApplicationException(e);
		}
	}

	/**
	 * 获取类型引用.
	 *
	 * @return 类型引用
	 */
	protected abstract TypeReference<E> getTypeReference();
}
