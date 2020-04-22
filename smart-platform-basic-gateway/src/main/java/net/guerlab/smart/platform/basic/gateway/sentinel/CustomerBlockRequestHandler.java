package net.guerlab.smart.platform.basic.gateway.sentinel;

import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import net.guerlab.web.result.Fail;
import net.guerlab.web.result.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

/**
 * 自定义限流处理
 *
 * @author guer
 */
public class CustomerBlockRequestHandler implements BlockRequestHandler {

    private static final String DEFAULT_BLOCK_MSG_PREFIX = "Blocked by Sentinel: ";

    private static Result<Void> buildErrorResult(Throwable ex) {
        return new Fail<>(DEFAULT_BLOCK_MSG_PREFIX + ex.getClass().getSimpleName(),
                HttpStatus.TOO_MANY_REQUESTS.value());
    }

    @Override
    public Mono<ServerResponse> handleRequest(ServerWebExchange exchange, Throwable ex) {
        return ServerResponse.status(HttpStatus.TOO_MANY_REQUESTS).contentType(MediaType.APPLICATION_JSON)
                .body(fromValue(buildErrorResult(ex)));
    }
}
