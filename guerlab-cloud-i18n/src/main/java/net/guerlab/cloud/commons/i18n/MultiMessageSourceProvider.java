package net.guerlab.cloud.commons.i18n;

/**
 * 多消息源处理提供者
 *
 * @author guer
 */
@FunctionalInterface
public interface MultiMessageSourceProvider {

    /**
     * 获取消息源
     *
     * @return 消息源
     */
    String get();
}
