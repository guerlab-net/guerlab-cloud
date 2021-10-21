package net.guerlab.cloud.sentinel.webflux.autoconfigure;

import com.alibaba.csp.sentinel.adapter.spring.webflux.callback.BlockRequestHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.util.function.Supplier;
import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author guer
 */
public class SentinelBlockExceptionHandler implements WebExceptionHandler {

    private final BlockRequestHandler blockRequestHandler;

    private final Supplier<ServerResponse.Context> contextSupplier;

    public SentinelBlockExceptionHandler(List<ViewResolver> viewResolvers, ServerCodecConfigurer serverCodecConfigurer,
            BlockRequestHandler blockRequestHandler) {
        this.blockRequestHandler = blockRequestHandler;
        contextSupplier = () -> new ServerResponse.Context() {

            @Override
            public List<HttpMessageWriter<?>> messageWriters() {
                return serverCodecConfigurer.getWriters();
            }

            @Override
            public List<ViewResolver> viewResolvers() {
                return viewResolvers;
            }
        };
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        if (exchange.getResponse().isCommitted()) {
            return Mono.error(ex);
        }
        // This exception handler only handles rejection by Sentinel.
        if (!BlockException.isBlockException(ex)) {
            return Mono.error(ex);
        }
        return blockRequestHandler.handleRequest(exchange, ex)
                .flatMap(response -> response.writeTo(exchange, contextSupplier.get()));
    }
}
