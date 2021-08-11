package net.guerlab.cloud.stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.config.BindingProperties;
import org.springframework.cloud.stream.config.BindingServiceProperties;

import java.util.Map;

/**
 * Binding目的地自动配置
 *
 * @author guer
 */
public abstract class BindingDestinationAutoConfigure {

    @Autowired
    public void initBindingDestination(BindingServiceProperties bindingServiceProperties) {
        Map<String, BindingProperties> bindings = bindingServiceProperties.getBindings();

        Map<String, String> bindingDestinations = getBindingDestinations();

        String suffix = putType().getSuffix();

        for (Map.Entry<String, String> entry : bindingDestinations.entrySet()) {
            String bindingName = entry.getKey() + suffix;
            if (bindings.containsKey(bindingName)) {
                continue;
            }

            BindingProperties bindingProperties = new BindingProperties();
            bindingProperties.setDestination(entry.getValue());

            bindings.put(bindingName, bindingProperties);
        }
    }

    /**
     * 获取推送类型
     *
     * @return 推送类型
     */
    protected abstract PutType putType();

    /**
     * 获取binding目的地列表
     *
     * @return binding目的地列表
     */
    protected abstract Map<String, String> getBindingDestinations();

}
