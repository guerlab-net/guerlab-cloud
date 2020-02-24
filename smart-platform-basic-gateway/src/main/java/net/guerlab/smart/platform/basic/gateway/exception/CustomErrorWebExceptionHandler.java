package net.guerlab.smart.platform.basic.gateway.exception;

import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.reactive.function.server.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * @author guer
 */
class CustomErrorWebExceptionHandler extends DefaultErrorWebExceptionHandler {

    CustomErrorWebExceptionHandler(ErrorAttributes errorAttributes, ResourceProperties resourceProperties,
            ErrorProperties errorProperties, ApplicationContext applicationContext) {
        super(errorAttributes, resourceProperties, errorProperties, applicationContext);
    }

    @Override
    protected Map<String, Object> getErrorAttributes(ServerRequest request, boolean includeStackTrace) {
        Throwable error = super.getError(request);

        Map<String, Object> errorAttributes = new HashMap<>(3);
        errorAttributes.put("status", false);
        errorAttributes.put("message", error.getLocalizedMessage());
        errorAttributes.put("errorCode", 0);

        if (error instanceof ResponseStatusException) {
            ResponseStatusException exception = (ResponseStatusException) error;
            errorAttributes.put("errorCode", 404);
            errorAttributes.put("message", exception.getReason());
        }

        return errorAttributes;
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    @NonNull
    @Override
    protected Mono<ServerResponse> renderErrorResponse(ServerRequest request) {
        return super.renderErrorResponse(request);
    }

    @Override
    protected int getHttpStatus(Map<String, Object> errorAttributes) {
        return HttpStatus.OK.value();
    }
}
