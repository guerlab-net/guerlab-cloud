package net.guerlab.cloud.stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.cloud.stream.config.BindingServiceProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * BindingDestinationAutoConfigure配置测试
 *
 * @author guer
 */
public class BindingDestinationAutoConfigureTest {

    private Map<String, String> bindingDestinations;

    private BindingServiceProperties bindingServiceProperties;

    @BeforeEach
    public void init() {
        bindingDestinations = new HashMap<>(3);
        bindingDestinations.put("testAdd", "test.add");
        bindingDestinations.put("testDelete", "test.delete");
        bindingDestinations.put("testUpdate", "test.update");
        bindingServiceProperties = new BindingServiceProperties();
        bindingServiceProperties.setBindings(new HashMap<>());
    }

    @Test
    public void input() {
        InputBindingDestinationAutoConfigure configure = new InputBindingDestinationAutoConfigure(bindingDestinations);
        configure.initBindingDestination(bindingServiceProperties);

        Assertions.assertArrayEquals(new String[] { "onTestAdd-in-0", "onTestDelete-in-0", "onTestUpdate-in-0" },
                bindingServiceProperties.getBindings().keySet().toArray(new String[] {}));
    }

    @Test
    public void output() {
        OutputBindingDestinationAutoConfigure configure = new OutputBindingDestinationAutoConfigure(
                bindingDestinations);
        configure.initBindingDestination(bindingServiceProperties);

        Assertions.assertArrayEquals(new String[] { "testAdd-out-0", "testDelete-out-0", "testUpdate-out-0" },
                bindingServiceProperties.getBindings().keySet().toArray(new String[] {}));
    }
}
