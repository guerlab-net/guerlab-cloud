package net.guerlab.cloud.web.webmvc.exception.handler.builder;

import net.guerlab.cloud.web.core.exception.handler.AbstractResponseBuilder;
import net.guerlab.cloud.web.webmvc.exception.MissingServletRequestParameterExceptionInfo;
import net.guerlab.web.result.Fail;
import org.springframework.web.bind.MissingServletRequestParameterException;

/**
 * MissingServletRequestParameterException异常处理
 *
 * @author guer
 */
public class MissingServletRequestParameterExceptionResponseBuilder extends AbstractResponseBuilder {

    @Override
    public boolean accept(Throwable e) {
        return e instanceof MissingServletRequestParameterException;
    }

    @Override
    public Fail<?> build(Throwable e) {
        MissingServletRequestParameterException exception = (MissingServletRequestParameterException) e;
        return buildByI18nInfo(new MissingServletRequestParameterExceptionInfo(exception), e);
    }
}
