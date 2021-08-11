package net.guerlab.cloud.stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.cloud.stream.config.BindingProperties;
import org.springframework.cloud.stream.config.BindingServiceConfiguration;
import org.springframework.cloud.stream.config.BindingServiceProperties;

import java.util.Map;

import static net.guerlab.cloud.stream.BindingNameConstants.DEFAULT_OUT_PARAM_SUFFIX;

/**
 * 消息Binding自动配置
 *
 * @author guer
 */
@AutoConfigureAfter(BindingServiceConfiguration.class)
public abstract class StreamBindingAutoConfigure {

    @Autowired
    public void initUserStreamProviderBinding(BindingServiceProperties bindingServiceProperties) {
        Map<String, BindingProperties> bindings = bindingServiceProperties.getBindings();

        Map<String, String> bindingDestinations = getBindingDestinations();

        for (String bindingName : bindingDestinations.keySet()) {
            bindingName = bindingName + DEFAULT_OUT_PARAM_SUFFIX;
            if (bindings.containsKey(bindingName)) {
                continue;
            }

            BindingProperties bindingProperties = new BindingProperties();
            bindingProperties.setDestination(bindingDestinations.get(bindingName));

            bindings.put(bindingName, bindingProperties);
        }
    }

    /**
     * 获取binding目的地列表
     *
     * @return binding目的地列表
     */
    protected abstract Map<String, String> getBindingDestinations();

}
