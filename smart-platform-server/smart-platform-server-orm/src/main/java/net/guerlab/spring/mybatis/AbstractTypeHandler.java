/*
 * Copyright 2018-2021 guerlab.net and other contributors.
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.guerlab.spring.mybatis;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.guerlab.commons.exception.ApplicationException;
import net.guerlab.spring.commons.util.SpringApplicationContextUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 抽象类型处理
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
    public final E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return this.toObject(rs.getString(columnName));
    }

    @Override
    public final E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return this.toObject(rs.getString(columnIndex));
    }

    @Override
    public final E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return this.toObject(cs.getString(columnIndex));
    }

    private ObjectMapper objectMapper() {
        return SpringApplicationContextUtil.getContext().getBean(ObjectMapper.class);
    }

    private String toJson(E object) {
        try {
            return objectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    private E toObject(String content) {
        String data = StringUtils.trimToNull(content);
        if (data == null) {
            return null;
        }

        try {
            return objectMapper().readValue(data, getTypeReference());
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * 获取类型引用
     *
     * @return 类型引用
     */
    protected abstract TypeReference<E> getTypeReference();
}
