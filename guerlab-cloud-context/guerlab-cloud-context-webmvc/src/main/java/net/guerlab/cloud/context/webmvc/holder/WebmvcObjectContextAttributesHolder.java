package net.guerlab.cloud.context.webmvc.holder;

import net.guerlab.cloud.context.core.ContextAttributes;
import net.guerlab.cloud.context.core.ObjectContextAttributesHolder;

import javax.servlet.http.HttpServletRequest;

/**
 * webmvc环境下基于对象的上下文属性持有器
 *
 * @author guer
 */
public class WebmvcObjectContextAttributesHolder implements ObjectContextAttributesHolder {

    @Override
    public boolean accept(Object object) {
        return object instanceof HttpServletRequest;
    }

    @Override
    public ContextAttributes get(Object object) {
        HttpServletRequest request = (HttpServletRequest) object;
        ContextAttributes contextAttributes = (ContextAttributes) request.getAttribute(ContextAttributes.KEY);

        if (contextAttributes == null) {
            contextAttributes = new ContextAttributes();
            request.setAttribute(ContextAttributes.KEY, contextAttributes);
        }

        return contextAttributes;
    }

    @Override
    public void set(Object object, ContextAttributes contextAttributes) {
        HttpServletRequest request = (HttpServletRequest) object;
        request.setAttribute(ContextAttributes.KEY, contextAttributes);
    }
}
