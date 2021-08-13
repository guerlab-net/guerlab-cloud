package net.guerlab.cloud.stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.cloud.stream.config.BindingProperties;
import org.springframework.cloud.stream.config.BindingServiceProperties;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * BindingDestinationAutoConfigure重复key测试
 *
 * @author guer
 */
public class RepeatKeyBindingDestinationAutoConfigureTest {

    private Map<String, String> bindingDestinations;

    private BindingServiceProperties bindingServiceProperties;

    @BeforeEach
    public void init() {
        bindingDestinations = new HashMap<>(3);
        bindingDestinations.put("testAdd", "test.add");

        Map<String, BindingProperties> binding = new HashMap<>();
        binding.put("testAdd-out-0", new BindingProperties());

        bindingServiceProperties = new BindingServiceProperties();
        bindingServiceProperties.setBindings(binding);
    }

    @Test
    public void test() {
        OutputBindingDestinationAutoConfigure configure = new OutputBindingDestinationAutoConfigure(
                bindingDestinations);
        configure.initBindingDestination(bindingServiceProperties);

        Set<String> keys = bindingServiceProperties.getBindings().keySet();

        Assertions.assertEquals(keys.size(), 1);
        Assertions.assertArrayEquals(new String[] { "testAdd-out-0" }, keys.toArray(new String[] {}));
    }
}
