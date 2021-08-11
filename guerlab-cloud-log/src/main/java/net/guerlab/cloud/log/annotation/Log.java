package net.guerlab.cloud.log.annotation;

import java.lang.annotation.*;

/**
 * 日志记录
 *
 * @author guer
 */
@SuppressWarnings("unused")
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Log {

    /**
     * 类型
     *
     * @return 类型
     */
    String type() default "";

    /**
     * 操作内容
     *
     * @return 操作内容
     */
    String value();
}
