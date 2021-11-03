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
package net.guerlab.cloud.web.core.annotation;

import java.lang.annotation.*;

/**
 * 忽略ResponseAdvisor的返回结果处理
 *
 * @author guer
 */
@Target({ ElementType.METHOD, ElementType.TYPE, ElementType.PACKAGE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface IgnoreResponseHandler {

}