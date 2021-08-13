package net.guerlab.cloud.stream;

import lombok.AllArgsConstructor;

/**
 * 推送类型
 *
 * @author guer
 */
@AllArgsConstructor
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

    /**
     * 格式化名称
     *
     * @param bindingName
     *         binding名称
     * @return 名称
     */
    public abstract String formatName(String bindingName);
}
