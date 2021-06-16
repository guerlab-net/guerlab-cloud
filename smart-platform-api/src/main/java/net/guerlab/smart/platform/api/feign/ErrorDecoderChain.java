package net.guerlab.smart.platform.api.feign;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.core.Ordered;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 错误解析器链
 *
 * @author guer
 */
public class ErrorDecoderChain implements ErrorDecoder {

    private List<OrderedErrorDecoder> decoders;

    public void setDecoders(List<OrderedErrorDecoder> decoders) {
        if (decoders == null) {
            return;
        }

        this.decoders = new ArrayList<>(decoders);
        decoders.sort(Comparator.comparingInt(Ordered::getOrder));
    }

    @Override
    public Exception decode(String methodKey, Response response) {
        Response reentrantResponse = reentrantResponse(response);
        Exception exception;
        if (decoders != null) {
            for (OrderedErrorDecoder decoder : decoders) {
                exception = decoder.decode(methodKey, reentrantResponse);
                if (exception != null) {
                    return exception;
                }
            }
        }
        return new ErrorDecoder.Default().decode(methodKey, reentrantResponse);
    }

    private Response reentrantResponse(Response response) {
        Response.Builder builder = Response.builder();

        if (response.body() != null) {
            try {
                builder.body(response.body().asInputStream().readAllBytes());
            } catch (IOException ignore) {
            }
        }

        return builder.headers(response.headers()).reason(response.reason()).request(response.request()).build();
    }
}
