package net.guerlab.smart.platform.basic.auth.token;

import net.guerlab.smart.platform.basic.auth.properties.TokenFactoryProperties;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 抽象token工厂
 *
 * @param <T>
 *         token实体类型
 * @param <P>
 *         配置类型
 * @author guer
 */
public abstract class AbstractTokenFactory<T, P extends TokenFactoryProperties> implements TokenFactory<T> {

    /**
     * 配置文件
     */
    protected P properties;

    @Override
    public boolean enabled() {
        return properties.isEnabled();
    }

    @Override
    public boolean isDefault() {
        return properties.isDefaultFactory();
    }

    protected static String getObjectValue(Object obj) {
        return obj == null ? "" : obj.toString();
    }

    /**
     * 获取前缀
     *
     * @return 前缀
     */
    @SuppressWarnings("SameReturnValue")
    protected abstract String getPrefix();

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public void setProperties(P properties) {
        this.properties = properties;
    }
}
