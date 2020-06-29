package net.guerlab.smart.platform.basic.auth.factory;

import net.guerlab.smart.platform.basic.auth.enums.TokenType;
import net.guerlab.smart.platform.basic.auth.properties.TokenFactoryProperties;
import net.guerlab.smart.platform.commons.ip.IpUtils;
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

    /**
     * 获取前缀
     *
     * @return 前缀
     */
    @SuppressWarnings("SameReturnValue")
    protected abstract String getPrefix();

    protected static String getObjectValue(Object obj) {
        return obj == null ? "" : obj.toString();
    }

    @Override
    public final String getRefreshTokenPrefix() {
        return getPrefix() + TokenFactory.CONNECTORS + TokenType.SIMPLE_NAME_REFRESH_TOKEN + TokenFactory.CONNECTORS;
    }

    @Override
    public final String getAccessTokenPrefix() {
        return getPrefix() + TokenFactory.CONNECTORS + TokenType.SIMPLE_NAME_ACCESS_TOKEN + TokenFactory.CONNECTORS;
    }

    @Override
    public final boolean acceptIp(String ip) {
        if (IpUtils.inList(properties.getDenyIpList(), ip)) {
            return false;
        }

        if (properties.getAllowIpList() == null || properties.getAllowIpList().isEmpty()) {
            return true;
        }

        return IpUtils.inList(properties.getAllowIpList(), ip);
    }

    @Autowired
    public void setProperties(P properties) {
        this.properties = properties;
    }

    @Override
    public final int getOrder() {
        return properties.getOrder();
    }
}
