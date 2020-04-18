package net.guerlab.spring.mybatis.typehandlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.guerlab.commons.exception.ApplicationException;
import net.guerlab.smart.platform.commons.domain.MultiString;
import net.guerlab.spring.commons.util.SpringApplicationContextUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * String集合类型处理
 *
 * @author guer
 */
@SuppressWarnings("WeakerAccess")
public class MultiStringTypeHandler extends BaseTypeHandler<MultiString> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, MultiString parameter, JdbcType jdbcType)
            throws SQLException {
        ps.setString(i, this.toJson(parameter));
    }

    @Override
    public MultiString getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return this.toObject(rs.getString(columnName));
    }

    @Override
    public MultiString getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return this.toObject(rs.getString(columnIndex));
    }

    @Override
    public MultiString getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return this.toObject(cs.getString(columnIndex));
    }

    private ObjectMapper objectMapper() {
        return SpringApplicationContextUtil.getContext().getBean(ObjectMapper.class);
    }

    private String toJson(MultiString object) {
        try {
            return objectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    private MultiString toObject(String content) {
        String data = StringUtils.trimToNull(content);
        if (data == null) {
            return null;
        }

        try {
            return objectMapper().readValue(data, MultiString.class);
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }
}
