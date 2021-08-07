package net.guerlab.cloud.log.annotation;

import java.lang.annotation.*;

/**
 * 日志记录
 *
 * @author guer
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Log {

    /**
     * 操作内容
     *
     * @return 操作内容
     */
    String value();
}
