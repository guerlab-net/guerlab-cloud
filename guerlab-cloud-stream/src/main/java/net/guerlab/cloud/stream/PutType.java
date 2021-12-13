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
package net.guerlab.cloud.stream;

/**
 * 推送类型
 *
 * @author guer
 */
public enum PutType {

    /**
     * 输入
     */
    IN(BindingNameConstants.DEFAULT_IN_PARAM_SUFFIX) {
        @Override
        public String formatName(String bindingName) {
            return MessageUtils.getListenerName(bindingName) + IN.suffix;
        }
    },

    /**
     * 输出
     */
    OUT(BindingNameConstants.DEFAULT_OUT_PARAM_SUFFIX) {
        @Override
        public String formatName(String bindingName) {
            return bindingName + OUT.suffix;
        }
    };

    /**
     * 后缀
     */
    private final String suffix;

    PutType(String suffix) {
        this.suffix = suffix;
    }

    /**
     * 格式化名称
     *
     * @param bindingName
     *         binding名称
     * @return 名称
     */
    public abstract String formatName(String bindingName);
}
