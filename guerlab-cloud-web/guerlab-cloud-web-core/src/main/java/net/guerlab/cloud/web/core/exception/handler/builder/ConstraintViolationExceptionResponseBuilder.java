package net.guerlab.cloud.web.core.exception.handler.builder;

import net.guerlab.cloud.web.core.exception.RequestParamsError;
import net.guerlab.cloud.web.core.exception.handler.AbstractRequestParamsErrorResponseBuilder;
import net.guerlab.web.result.Fail;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * ConstraintViolationException异常处理
 *
 * @author guer
 */
public class ConstraintViolationExceptionResponseBuilder extends AbstractRequestParamsErrorResponseBuilder {

    @Override
    public boolean accept(Throwable e) {
        return e instanceof ConstraintViolationException;
    }

    @Override
    public Fail<?> build(Throwable e) {
        ConstraintViolationException exception = (ConstraintViolationException) e;

        Collection<ConstraintViolation<?>> constraintViolations = exception.getConstraintViolations();

        Collection<String> displayMessageList = constraintViolations.stream().map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());
        return build0(new RequestParamsError(exception, displayMessageList));
    }
}
