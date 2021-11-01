package net.guerlab.cloud.context.webflux.holder;

import net.guerlab.cloud.context.core.ContextAttributes;
import net.guerlab.cloud.context.core.ObjectContextAttributesHolder;
import org.springframework.web.server.ServerWebExchange;

/**
 * webflux环境下基于对象的上下文属性持有器
 *
 * @author guer
 */
public class WebfluxObjectContextAttributesHolder implements ObjectContextAttributesHolder {

    @Override
    public boolean accept(Object object) {
        return object instanceof ServerWebExchange;
    }

    @Override
    public ContextAttributes get(Object object) {
        ServerWebExchange exchange = (ServerWebExchange) object;
        ContextAttributes contextAttributes = (ContextAttributes) exchange.getAttributes().get(ContextAttributes.KEY);

        if (contextAttributes == null) {
            contextAttributes = new ContextAttributes();
            exchange.getAttributes().put(ContextAttributes.KEY, contextAttributes);
        }

        return contextAttributes;
    }

    @Override
    public void set(Object object, ContextAttributes contextAttributes) {
        ServerWebExchange exchange = (ServerWebExchange) object;
        exchange.getAttributes().put(ContextAttributes.KEY, contextAttributes);
    }
}
