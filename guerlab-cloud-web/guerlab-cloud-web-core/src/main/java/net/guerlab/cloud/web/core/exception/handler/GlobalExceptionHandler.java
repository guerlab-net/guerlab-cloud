/*
 * Copyright 2018-2022 guerlab.net and other contributors.
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.gnu.org/licenses/lgpl-3.0.html
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.guerlab.cloud.web.core.exception.handler;

import lombok.Getter;
import net.guerlab.cloud.commons.exception.handler.ResponseBuilder;
import net.guerlab.cloud.commons.exception.handler.StackTracesHandler;
import net.guerlab.cloud.core.result.Fail;
import org.springframework.context.MessageSource;

import java.util.Collection;

/**
 * 异常统一处理配置
 *
 * @author guer
 */
public class GlobalExceptionHandler {

    protected final MessageSource messageSource;

    protected final StackTracesHandler stackTracesHandler;

    @Getter
    protected final GlobalExceptionLogger globalExceptionLogger;

    protected final Collection<ResponseBuilder> builders;

    private final ThrowableResponseBuilder defaultBuilder;

    public GlobalExceptionHandler(MessageSource messageSource, StackTracesHandler stackTracesHandler,
            GlobalExceptionLogger globalExceptionLogger, Collection<ResponseBuilder> builders) {
        this.messageSource = messageSource;
        this.stackTracesHandler = stackTracesHandler;
        this.globalExceptionLogger = globalExceptionLogger;
        this.builders = builders;

        defaultBuilder = new ThrowableResponseBuilder();
        defaultBuilder.setStackTracesHandler(stackTracesHandler);
        defaultBuilder.setMessageSource(messageSource);

        builders.forEach(builder -> {
            builder.setStackTracesHandler(stackTracesHandler);
            builder.setMessageSource(messageSource);
        });
    }

    /**
     * 异常信息构建处理
     *
     * @param e
     *         异常
     * @return 异常信息
     */
    public Fail<?> build(Throwable e) {
        return builders.stream().sorted().filter(builder -> builder.accept(e)).findFirst().orElse(defaultBuilder)
                .build(e);
    }
}
