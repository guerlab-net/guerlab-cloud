package net.guerlab.smart.platform.server.utils;

import net.guerlab.spring.commons.util.SpringApplicationContextUtil;
import org.springframework.core.env.Environment;

/**
 * spring 工具类
 *
 * @author guer
 */
@SuppressWarnings("WeakerAccess")
public class SpringUtils {

    private SpringUtils() {

    }

    /**
     * 获取环境信息
     *
     * @return 环境信息
     */
    private static Environment getEnvironment() {
        return SpringApplicationContextUtil.getContext().getEnvironment();
    }

    /**
     * 获取应用名称
     *
     * @return 应用名称
     */
    public static String getApplicationName() {
        return getEnvironment().getProperty("spring.application.name");
    }
}
