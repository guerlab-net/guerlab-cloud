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
package net.guerlab.cloud.stream;

/**
 * Binding名称常量
 *
 * @author guer
 */
@SuppressWarnings("unused")
public interface BindingNameConstants {

    /**
     * 输出参数后缀
     */
    String OUT_PARAM_SUFFIX = "-out-";

    /**
     * 默认输出参数后缀
     */
    String DEFAULT_OUT_PARAM_SUFFIX = OUT_PARAM_SUFFIX + "0";

    /**
     * 输入参数后缀
     */
    String IN_PARAM_SUFFIX = "-in-";

    /**
     * 默认输入参数后缀
     */
    String DEFAULT_IN_PARAM_SUFFIX = IN_PARAM_SUFFIX + "0";

}
