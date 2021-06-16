package net.guerlab.smart.platform.api.feign;

import feign.Response;
import org.springframework.core.Ordered;

/**
 * @author guer
 */
public interface OrderedErrorDecoder extends Ordered {

    /**
     * Implement this method in order to decode an HTTP {@link Response} when
     * {@link Response#status()} is not in the 2xx range. Please raise application-specific exceptions
     * where possible. If your exception is retryable, wrap or subclass {@link feign.RetryableException}
     *
     * @param methodKey
     *         {@link feign.Feign#configKey} of the java method that invoked the request. ex.
     *         {@code IAM#getUser()}
     * @param response
     *         HTTP response where {@link Response#status() status} is greater than or equal
     *         to {@code 300}.
     * @return Exception IOException, if there was a network error reading the response or an
     * application-specific exception decoded by the implementation. If the throwable is
     * retryable, it should be wrapped, or a subtype of {@link feign.RetryableException}
     */
    Exception decode(String methodKey, Response response);
}
