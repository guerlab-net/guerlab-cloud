package net.guerlab.smart.platform.commons.version;

import net.guerlab.smart.platform.commons.exception.CurrentVersionInvalidException;
import net.guerlab.smart.platform.commons.exception.UnsupportedVersionFieldTypeException;
import tk.mybatis.mapper.version.NextVersion;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 默认下一个版本号生成
 *
 * @author guer
 */
public class DefaultNextVersion implements NextVersion<Object> {

    @Override
    public Object nextVersion(Object current) {
        if (current == null) {
            throw new CurrentVersionInvalidException();
        }
        if (current instanceof Integer) {
            return (Integer) current + 1;
        } else if (current instanceof Long) {
            return (Long) current + 1L;
        } else if (current instanceof BigDecimal) {
            return ((BigDecimal) current).add(BigDecimal.ONE);
        } else if (current instanceof LocalDateTime) {
            return LocalDateTime.now();
        } else if (current instanceof Timestamp) {
            return new Timestamp(System.currentTimeMillis());
        } else if (current instanceof Date) {
            return new Date();
        } else {
            throw new UnsupportedVersionFieldTypeException();
        }
    }

}
