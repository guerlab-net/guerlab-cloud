package net.guerlab.smart.platform.server.sentinel.autoconfigure;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import net.guerlab.commons.exception.ApplicationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义限流处理自动配置
 *
 * @author guer
 */
@Configuration
public class BlockExceptionHandlerAutoconfigure {

    /**
     * 构造自定义限流处理
     *
     * @return 自定义限流处理
     */
    @Bean
    public CustomerBlockExceptionHandler customerBlockExceptionHandler() {
        return new CustomerBlockExceptionHandler();
    }

    /**
     * 自定义限流处理
     *
     * @author guer
     */
    public static class CustomerBlockExceptionHandler implements BlockExceptionHandler {

        private static final String DEFAULT_BLOCK_MSG_PREFIX = "Blocked by Sentinel: ";

        @Override
        public void handle(HttpServletRequest request, HttpServletResponse response, BlockException ex) {
            throw new ApplicationException(DEFAULT_BLOCK_MSG_PREFIX + ex.getClass().getSimpleName(),
                    HttpStatus.TOO_MANY_REQUESTS.value());
        }
    }
}
