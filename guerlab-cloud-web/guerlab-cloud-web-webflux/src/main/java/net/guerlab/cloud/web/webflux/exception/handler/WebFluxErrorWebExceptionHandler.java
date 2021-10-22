package net.guerlab.cloud.web.webflux.exception.handler;

import lombok.extern.slf4j.Slf4j;
import net.guerlab.cloud.web.core.exception.handler.GlobalExceptionHandler;
import net.guerlab.web.result.Fail;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.RequestPath;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

/**
 * 异常处理
 *
 * @author guer
 */
@Slf4j
public class WebFluxErrorWebExceptionHandler extends DefaultErrorWebExceptionHandler {

    private final GlobalExceptionHandler globalExceptionHandler;

    public WebFluxErrorWebExceptionHandler(ErrorAttributes errorAttributes, WebProperties.Resources resources,
            ErrorProperties errorProperties, ApplicationContext applicationContext,
            GlobalExceptionHandler globalExceptionHandler) {
        super(errorAttributes, resources, errorProperties, applicationContext);
        this.globalExceptionHandler = globalExceptionHandler;
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    @Override
    protected Mono<ServerResponse> renderErrorResponse(ServerRequest request) {
        Throwable error = getError(request);
        globalExceptionHandler.getGlobalExceptionLogger().debug(error, request.methodName(), parseRequestUri(request));

        Fail<?> fail = globalExceptionHandler.build(error);

        return ServerResponse.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(fail));
    }

    private String parseRequestUri(ServerRequest request) {
        RequestPath requestPath = request.requestPath();
        String contextPath = StringUtils.trimToNull(requestPath.contextPath().value());
        String requestUri = requestPath.value();

        if (contextPath != null) {
            String newRequestUri = requestUri.replaceFirst(contextPath, "");
            log.debug("replace requestUri[form={}, to={}]", requestUri, newRequestUri);
            requestUri = newRequestUri;
        }

        return requestUri;
    }
}
