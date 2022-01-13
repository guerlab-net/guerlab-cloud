/*
 * Copyright 2018-2022 guerlab.net and other contributors.
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
package net.guerlab.cloud.log.annotation;

import java.lang.annotation.*;

/**
 * 日志记录分组
 *
 * @author guer
 */
@SuppressWarnings("unused")
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface LogGroup {

    /**
     * 类型
     *
     * @return 类型
     */
    String type() default "";

    /**
     * 分组名称
     *
     * @return 分组名称
     */
    String value();
}
