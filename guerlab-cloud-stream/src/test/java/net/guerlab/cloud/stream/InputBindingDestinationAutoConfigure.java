package net.guerlab.cloud.stream;

import lombok.AllArgsConstructor;

import java.util.Map;

/**
 * 输入流配置
 *
 * @author guer
 */
@AllArgsConstructor
public class InputBindingDestinationAutoConfigure extends AbstractBindingDestinationAutoConfigure {

    private final Map<String, String> bindingDestinations;

    @Override
    protected PutType putType() {
        return PutType.IN;
    }

    @Override
    protected Map<String, String> getBindingDestinations() {
        return bindingDestinations;
    }
}
