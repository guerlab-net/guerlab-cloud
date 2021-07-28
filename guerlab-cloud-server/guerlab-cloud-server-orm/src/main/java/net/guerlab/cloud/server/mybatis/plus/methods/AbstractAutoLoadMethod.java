package net.guerlab.cloud.server.mybatis.plus.methods;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import net.guerlab.cloud.commons.entity.BaseEntity;
import net.guerlab.commons.reflection.FieldUtil;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.stream.Stream;

/**
 * 自动加载注入方法
 *
 * @author guer
 */
public abstract class AbstractAutoLoadMethod extends AbstractMethod {

    protected static final Collection<Field> BASE_ENTITY_FIELDS = FieldUtil.getFields(BaseEntity.class);

    protected static Stream<TableFieldInfo> getFieldStream(TableInfo tableInfo) {
        return tableInfo.getFieldList().stream().filter(tableField -> filedFilter(tableInfo, tableField));
    }

    protected static boolean filedFilter(TableInfo tableInfo, TableFieldInfo fieldInfo) {
        if (BaseEntity.class.isAssignableFrom(tableInfo.getEntityType())) {
            return !BASE_ENTITY_FIELDS.contains(fieldInfo.getField());
        }
        return true;
    }
}
