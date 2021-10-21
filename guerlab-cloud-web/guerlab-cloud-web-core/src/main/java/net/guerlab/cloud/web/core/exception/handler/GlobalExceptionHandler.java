/*
 * Copyright 2018-2021 guerlab.net and other contributors.
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.guerlab.cloud.web.core.exception.handler;

import net.guerlab.cloud.web.core.exception.*;
import net.guerlab.cloud.web.core.properties.GlobalExceptionProperties;
import net.guerlab.commons.exception.ApplicationException;
import net.guerlab.spring.commons.exception.AbstractI18nApplicationException;
import net.guerlab.web.result.Fail;
import net.guerlab.web.result.RemoteException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpMethod;
import org.springframework.lang.Nullable;
import org.springframework.util.AntPathMatcher;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Collection;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * 异常统一处理配置
 *
 * @author guer
 */
@SuppressWarnings("SpringJavaAutowiredMembersInspection")
public class GlobalExceptionHandler {

    protected static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    protected final AntPathMatcher matcher = new AntPathMatcher();

    protected MessageSource messageSource;

    protected GlobalExceptionProperties properties;

    protected StackTracesHandler stackTracesHandler;

    @Autowired
    public void setProperties(GlobalExceptionProperties properties) {
        this.properties = properties;
    }

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Autowired
    public void setStackTracesHandler(StackTracesHandler stackTracesHandler) {
        this.stackTracesHandler = stackTracesHandler;
    }

    /**
     * MethodArgumentTypeMismatchException异常处理
     *
     * @param request
     *         请求
     * @param e
     *         异常
     * @return 响应数据
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Fail<Void> methodArgumentTypeMismatchException(HttpServletRequest request,
            MethodArgumentTypeMismatchException e) {
        debug(request, e);
        return handler0(new MethodArgumentTypeMismatchExceptionInfo(e));
    }

    /**
     * HttpRequestMethodNotSupportedException异常处理
     *
     * @param request
     *         请求
     * @param e
     *         异常
     * @return 响应数据
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Fail<Void> httpRequestMethodNotSupportedException(HttpServletRequest request,
            HttpRequestMethodNotSupportedException e) {
        debug(request, e);
        return handler0(new HttpRequestMethodNotSupportedExceptionInfo(e));
    }

    /**
     * MissingServletRequestParameterException异常处理
     *
     * @param request
     *         请求
     * @param e
     *         异常
     * @return 响应数据
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Fail<Void> missingServletRequestParameterException(HttpServletRequest request,
            MissingServletRequestParameterException e) {
        debug(request, e);
        return handler0(new MissingServletRequestParameterExceptionInfo(e));
    }

    /**
     * MethodArgumentNotValidException异常处理
     *
     * @param request
     *         请求
     * @param e
     *         异常
     * @return 响应数据
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Fail<Collection<String>> methodArgumentNotValidException(HttpServletRequest request,
            MethodArgumentNotValidException e) {
        debug(request, e);

        BindingResult bindingResult = e.getBindingResult();

        Collection<String> displayMessageList = bindingResult.getAllErrors().stream()
                .map(this::getMethodArgumentNotValidExceptionDisplayMessage).collect(Collectors.toList());

        return handler0(new RequestParamsError(e, displayMessageList));
    }

    private String getMethodArgumentNotValidExceptionDisplayMessage(ObjectError error) {
        String defaultMessage = error.getDefaultMessage();

        if (defaultMessage == null) {
            return error.getObjectName() + error.getDefaultMessage();
        }

        try {
            return messageSource.getMessage(defaultMessage, null, LocaleContextHolder.getLocale());
        } catch (NoSuchMessageException e) {
            return error.getObjectName() + error.getDefaultMessage();
        }
    }

    /**
     * ConstraintViolationException异常处理
     *
     * @param request
     *         请求
     * @param e
     *         异常
     * @return 响应数据
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Fail<Collection<String>> constraintViolationException(HttpServletRequest request,
            ConstraintViolationException e) {
        debug(request, e);

        Collection<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();

        Collection<String> displayMessageList = constraintViolations.stream().map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());

        return handler0(new RequestParamsError(e, displayMessageList));
    }

    /**
     * AbstractI18nApplicationException异常处理
     *
     * @param request
     *         请求
     * @param e
     *         异常
     * @return 响应数据
     */
    @ExceptionHandler(AbstractI18nApplicationException.class)
    public Fail<Void> abstractI18nApplicationException(HttpServletRequest request, AbstractI18nApplicationException e) {
        debug(request, e);
        String message = e.getMessage(messageSource);
        return new Fail<>(message, e.getErrorCode());
    }

    /**
     * ApplicationException异常处理
     *
     * @param request
     *         请求
     * @param e
     *         异常
     * @return 响应数据
     */
    @ExceptionHandler(ApplicationException.class)
    public Fail<Void> applicationException(HttpServletRequest request, ApplicationException e) {
        debug(request, e);
        String message = getMessage(e.getLocalizedMessage());
        Fail<Void> fail = new Fail<>(message, e.getErrorCode());
        stackTracesHandler.setStackTrace(fail, e);
        return fail;
    }

    /**
     * RemoteException异常处理
     *
     * @param request
     *         请求
     * @param e
     *         异常
     * @return 响应数据
     */
    @ExceptionHandler(RemoteException.class)
    public Fail<Void> remoteException(HttpServletRequest request, RemoteException e) {
        debug(request, e);
        String message = getMessage(e.getLocalizedMessage());
        Fail<Void> fail = new Fail<>(message);
        fail.setStackTraces(e.getApplicationStackTraces());
        return fail;
    }

    /**
     * 通用异常处理
     *
     * @param request
     *         请求
     * @param e
     *         异常
     * @return 响应数据
     */
    @ExceptionHandler(Exception.class)
    public Fail<Void> exception(HttpServletRequest request, Exception e) {
        debug(request, e);

        ResponseStatus responseStatus = AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class);

        Fail<Void> fail;
        if (responseStatus != null) {
            int errorCode = responseStatus.value().value();
            String message = responseStatus.reason();

            fail = new Fail<>(getMessage(message), errorCode);
            stackTracesHandler.setStackTrace(fail, e.getCause());
        } else if (StringUtils.isBlank(e.getMessage())) {
            fail = handler0(new DefaultExceptionInfo(e));
        } else {
            fail = new Fail<>(getMessage(e.getLocalizedMessage()));
            stackTracesHandler.setStackTrace(fail, e.getCause());
        }
        return fail;
    }

    protected void debug(HttpServletRequest request, Throwable e) {
        String requestMethod = request.getMethod();
        String requestPath = request.getRequestURI();

        for (GlobalExceptionProperties.Url url : properties.getLogIgnorePaths()) {
            String path = StringUtils.trimToNull(url.getPath());
            if (path == null) {
                continue;
            }
            HttpMethod method = url.getMethod();
            if (method != null && method.matches(requestMethod)) {
                continue;
            }
            if (matcher.match(path, requestPath)) {
                continue;
            }

            LOGGER.debug(String.format("request uri[%s %s]", request.getMethod(), request.getRequestURI()), e);
        }
    }

    protected Fail<Collection<String>> handler0(RequestParamsError e) {
        String message = getMessage(e.getLocalizedMessage());
        Fail<Collection<String>> fail = new Fail<>(message, e.getErrorCode());
        fail.setData(e.getErrors());
        stackTracesHandler.setStackTrace(fail, e.getCause());
        return fail;
    }

    protected Fail<Void> handler0(AbstractI18nInfo i18nInfo) {
        String message = i18nInfo.getMessage(messageSource);
        Fail<Void> fail = new Fail<>(message, i18nInfo.getErrorCode());
        stackTracesHandler.setStackTrace(fail, i18nInfo.getThrowable());
        return fail;
    }

    @Nullable
    protected String getMessage(String msg) {
        if (StringUtils.isBlank(msg)) {
            return msg;
        }

        Locale locale = LocaleContextHolder.getLocale();

        return messageSource.getMessage(msg, null, msg, locale);
    }

}
