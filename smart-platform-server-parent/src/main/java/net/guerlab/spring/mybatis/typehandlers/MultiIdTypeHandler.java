package net.guerlab.spring.mybatis.typehandlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.guerlab.commons.exception.ApplicationException;
import net.guerlab.smart.platform.commons.domain.MultiId;
import net.guerlab.spring.commons.util.SpringApplicationContextUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * ID集合类型处理
 *
 * @author guer
 */
@SuppressWarnings("WeakerAccess")
public class MultiIdTypeHandler extends BaseTypeHandler<MultiId> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, MultiId parameter, JdbcType jdbcType)
            throws SQLException {
        ps.setString(i, this.toJson(parameter));
    }

    @Override
    public MultiId getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return this.toObject(rs.getString(columnName));
    }

    @Override
    public MultiId getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return this.toObject(rs.getString(columnIndex));
    }

    @Override
    public MultiId getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return this.toObject(cs.getString(columnIndex));
    }

    private ObjectMapper objectMapper() {
        return SpringApplicationContextUtil.getContext().getBean(ObjectMapper.class);
    }

    private String toJson(MultiId object) {
        try {
            return objectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    private MultiId toObject(String content) {
        String data = StringUtils.trimToNull(content);
        if (data == null) {
            return null;
        }

        try {
            return objectMapper().readValue(data, MultiId.class);
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }
}
