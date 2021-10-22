package net.guerlab.cloud.web.core.exception.handler.builder;

import net.guerlab.cloud.web.core.exception.MethodArgumentTypeMismatchExceptionInfo;
import net.guerlab.cloud.web.core.exception.handler.AbstractResponseBuilder;
import net.guerlab.web.result.Fail;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * MethodArgumentTypeMismatchException异常处理
 *
 * @author guer
 */
public class MethodArgumentTypeMismatchExceptionResponseBuilder extends AbstractResponseBuilder {

    @Override
    public boolean accept(Throwable e) {
        return e instanceof MethodArgumentTypeMismatchException;
    }

    @Override
    public Fail<?> build(Throwable e) {
        MethodArgumentTypeMismatchException exception = (MethodArgumentTypeMismatchException) e;
        return buildByI18nInfo(new MethodArgumentTypeMismatchExceptionInfo((exception)), e);
    }
}
