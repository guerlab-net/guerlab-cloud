package net.guerlab.cloud.web.core.exception.handler;

import net.guerlab.cloud.web.core.exception.DefaultExceptionInfo;
import net.guerlab.web.result.Fail;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 通用异常处理
 *
 * @author guer
 */
public class ThrowableResponseBuilder extends AbstractResponseBuilder {

    @Override
    public boolean accept(Throwable e) {
        return true;
    }

    @Override
    public Fail<?> build(Throwable e) {
        ResponseStatus responseStatus = AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class);

        Fail<?> fail;
        if (responseStatus != null) {
            int errorCode = responseStatus.value().value();
            String message = responseStatus.reason();

            fail = new Fail<>(getMessage(message), errorCode);
            stackTracesHandler.setStackTrace(fail, e.getCause());
        } else if (StringUtils.isBlank(e.getMessage())) {
            fail = buildByI18nInfo(new DefaultExceptionInfo(e), e);
        } else {
            fail = new Fail<>(getMessage(e.getLocalizedMessage()));
            stackTracesHandler.setStackTrace(fail, e.getCause());
        }
        return fail;
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
