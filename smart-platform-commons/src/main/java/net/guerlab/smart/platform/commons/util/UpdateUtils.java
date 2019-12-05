package net.guerlab.smart.platform.commons.util;

import java.util.Objects;

/**
 * 更新工具类
 *
 * @author guer
 */
public class UpdateUtils {

    private UpdateUtils() {

    }

    /**
     * 获取更新内容，当新旧内容一致时返回null，否则返回新内容
     *
     * @param newData
     *         新内容
     * @param oldData
     *         旧内容
     * @param <T>
     *         内容格式
     * @return 更新内容
     */
    public static <T> T getUpdateValue(T newData, T oldData) {
        return Objects.equals(newData, oldData) ? null : newData;
    }
}
