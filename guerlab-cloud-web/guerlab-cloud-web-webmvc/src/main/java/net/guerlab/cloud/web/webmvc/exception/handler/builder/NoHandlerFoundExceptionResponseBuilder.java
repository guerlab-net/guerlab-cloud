package net.guerlab.cloud.web.webmvc.exception.handler.builder;

import net.guerlab.cloud.web.core.exception.handler.AbstractResponseBuilder;
import net.guerlab.cloud.web.webmvc.exception.NoHandlerFoundExceptionInfo;
import net.guerlab.web.result.Fail;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * NoHandlerFoundException异常处理
 *
 * @author guer
 */
public class NoHandlerFoundExceptionResponseBuilder extends AbstractResponseBuilder {

    @Override
    public boolean accept(Throwable e) {
        return e instanceof NoHandlerFoundException;
    }

    @Override
    public Fail<?> build(Throwable e) {
        NoHandlerFoundException exception = (NoHandlerFoundException) e;
        return buildByI18nInfo(new NoHandlerFoundExceptionInfo(exception), e);
    }
}
