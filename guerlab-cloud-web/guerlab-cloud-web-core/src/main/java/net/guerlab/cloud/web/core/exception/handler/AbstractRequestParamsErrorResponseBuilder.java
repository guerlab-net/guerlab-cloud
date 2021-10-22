package net.guerlab.cloud.web.core.exception.handler;

import net.guerlab.cloud.web.core.exception.RequestParamsError;
import net.guerlab.web.result.Fail;

import java.util.Collection;

/**
 * 抽象请求参数错误响应构建
 *
 * @author guer
 */
public abstract class AbstractRequestParamsErrorResponseBuilder extends AbstractResponseBuilder {

    protected Fail<Collection<String>> build0(RequestParamsError e) {
        String message = getMessage(e.getLocalizedMessage());
        Fail<Collection<String>> fail = new Fail<>(message, e.getErrorCode());
        fail.setData(e.getErrors());
        stackTracesHandler.setStackTrace(fail, e);
        return fail;
    }
}
