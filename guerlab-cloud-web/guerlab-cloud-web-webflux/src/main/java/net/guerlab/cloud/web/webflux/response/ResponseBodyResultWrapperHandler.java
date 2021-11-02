package net.guerlab.cloud.web.webflux.response;

import lombok.extern.slf4j.Slf4j;
import net.guerlab.cloud.web.core.response.ResponseBodyWrapperSupport;
import net.guerlab.cloud.web.webflux.utils.RequestUtils;
import net.guerlab.web.result.Result;
import net.guerlab.web.result.Succeed;
import org.springframework.core.MethodParameter;
import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.lang.Nullable;
import org.springframework.web.reactive.HandlerResult;
import org.springframework.web.reactive.accept.RequestedContentTypeResolver;
import org.springframework.web.reactive.result.method.annotation.ResponseBodyResultHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 响应内容结果包装处理
 *
 * @author guer
 */
@Slf4j
public class ResponseBodyResultWrapperHandler extends ResponseBodyResultHandler {

    private static final MethodParameter METHOD_PARAMETER_WITH_MONO_RESULT;

    static {
        try {
            METHOD_PARAMETER_WITH_MONO_RESULT = new MethodParameter(
                    ResponseBodyResultWrapperHandler.class.getDeclaredMethod("methodForParams"), -1);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private final ResponseBodyWrapperSupport support;

    public ResponseBodyResultWrapperHandler(List<HttpMessageWriter<?>> writers, RequestedContentTypeResolver resolver,
            ResponseBodyWrapperSupport support) {
        super(writers, resolver);
        this.support = support;
        setOrder(-10);
    }

    @Nullable
    private static Mono<Result<?>> methodForParams() {
        return null;
    }

    @Override
    public boolean supports(HandlerResult result) {
        return support.notHasAnnotation(result.getReturnTypeSource());
    }

    @Override
    public Mono<Void> handleResult(ServerWebExchange exchange, HandlerResult result) {
        Object body = result.getReturnValue();
        MethodParameter bodyTypeParameter = result.getReturnTypeSource();
        if (support.noConvertObject(body) || matchExcluded(exchange, bodyTypeParameter.getMethod())) {
            return writeBody(body, bodyTypeParameter, exchange);
        }

        if (body != null) {
            Mono<?> mono = null;
            if (body instanceof Mono) {
                mono = (Mono<?>) body;
            } else if (body instanceof Flux) {
                Flux<?> flux = (Flux<?>) body;
                mono = flux.collectList();
            }

            if (mono != null) {
                body = mono.map(Succeed::new).defaultIfEmpty(new Succeed<>());
            } else {
                body = new Succeed<>(body);
            }
        } else {
            body = new Succeed<>();
        }

        return writeBody(body, METHOD_PARAMETER_WITH_MONO_RESULT, exchange);
    }

    private boolean matchExcluded(ServerWebExchange exchange, @Nullable Method method) {
        return support.matchExcluded(getRequestPath(exchange), method);
    }

    private String getRequestPath(ServerWebExchange exchange) {
        return RequestUtils.parseRequestUri(exchange.getRequest());
    }
}
