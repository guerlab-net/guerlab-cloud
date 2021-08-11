package net.guerlab.cloud.stream;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 推送类型
 *
 * @author guer
 */
@Getter
@AllArgsConstructor
public enum PutType {

    /**
     * 输入
     */
    IN(BindingNameConstants.DEFAULT_IN_PARAM_SUFFIX),

    /**
     * 输出
     */
    OUT(BindingNameConstants.DEFAULT_OUT_PARAM_SUFFIX);

    private final String suffix;
}
