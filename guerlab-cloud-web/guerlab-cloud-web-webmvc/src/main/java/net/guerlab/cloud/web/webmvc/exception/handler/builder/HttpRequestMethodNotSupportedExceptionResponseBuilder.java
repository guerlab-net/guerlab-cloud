package net.guerlab.cloud.web.webmvc.exception.handler.builder;

import net.guerlab.cloud.web.core.exception.handler.AbstractResponseBuilder;
import net.guerlab.cloud.web.webmvc.exception.HttpRequestMethodNotSupportedExceptionInfo;
import net.guerlab.web.result.Fail;
import org.springframework.web.HttpRequestMethodNotSupportedException;

/**
 * HttpRequestMethodNotSupportedException异常处理
 *
 * @author guer
 */
public class HttpRequestMethodNotSupportedExceptionResponseBuilder extends AbstractResponseBuilder {

    @Override
    public boolean accept(Throwable e) {
        return e instanceof HttpRequestMethodNotSupportedException;
    }

    @Override
    public Fail<?> build(Throwable e) {
        HttpRequestMethodNotSupportedException exception = (HttpRequestMethodNotSupportedException) e;
        return buildByI18nInfo(new HttpRequestMethodNotSupportedExceptionInfo(exception), e);
    }
}
