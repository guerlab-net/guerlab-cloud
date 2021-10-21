package net.guerlab.cloud.sentinel.webflux.autoconfigure;

import com.alibaba.csp.sentinel.adapter.spring.webflux.callback.BlockRequestHandler;
import net.guerlab.cloud.web.core.exception.handler.StackTracesHandler;
import net.guerlab.web.result.Fail;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

/**
 * 阻塞请求处理
 *
 * @author guer
 */
public class CustomerBlockRequestHandler implements BlockRequestHandler {

    private static final String DEFAULT_BLOCK_MSG_PREFIX = "Blocked by Sentinel: ";

    private final StackTracesHandler stackTracesHandler;

    public CustomerBlockRequestHandler(StackTracesHandler stackTracesHandler) {
        this.stackTracesHandler = stackTracesHandler;
    }

    @Override
    public Mono<ServerResponse> handleRequest(ServerWebExchange exchange, Throwable ex) {
        // JSON result by default.
        return ServerResponse.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
                .body(fromValue(buildErrorResult(ex)));
    }

    private Fail<Void> buildErrorResult(Throwable ex) {
        Fail<Void> result = new Fail<>();
        result.setErrorCode(HttpStatus.TOO_MANY_REQUESTS.value());
        result.setMessage(DEFAULT_BLOCK_MSG_PREFIX + ex.getClass().getSimpleName());

        stackTracesHandler.setStackTrace(result, ex);

        return result;
    }
}
