package net.guerlab.cloud.stream;

import lombok.AllArgsConstructor;

import java.util.Map;

/**
 * 输出流配置
 *
 * @author guer
 */
@AllArgsConstructor
public class OutputBindingDestinationAutoConfigure extends AbstractBindingDestinationAutoConfigure {

    private final Map<String, String> bindingDestinations;

    @Override
    protected PutType putType() {
        return PutType.OUT;
    }

    @Override
    protected Map<String, String> getBindingDestinations() {
        return bindingDestinations;
    }
}
