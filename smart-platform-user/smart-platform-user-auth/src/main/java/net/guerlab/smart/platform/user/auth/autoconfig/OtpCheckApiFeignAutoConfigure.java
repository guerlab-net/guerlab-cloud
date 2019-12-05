package net.guerlab.smart.platform.user.auth.autoconfig;

import lombok.AllArgsConstructor;
import net.guerlab.smart.platform.user.auth.OtpCheckApi;
import net.guerlab.smart.platform.user.auth.feign.FeignAuthApi;
import net.guerlab.smart.platform.user.core.entity.OtpCheckRequest;
import net.guerlab.smart.platform.user.core.entity.OtpCheckResponse;
import net.guerlab.web.result.Result;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author guer
 */
@Configuration
@AutoConfigureAfter(OtpCheckApiLocalServiceAutoConfigure.class)
public class OtpCheckApiFeignAutoConfigure {

    @Bean
    @ConditionalOnMissingBean(OtpCheckApi.class)
    public OtpCheckApi otpCheckApiFeignWrapper(FeignAuthApi client) {
        return new OtpCheckApiFeignWrapper(client);
    }

    @AllArgsConstructor
    private static class OtpCheckApiFeignWrapper implements OtpCheckApi {

        private FeignAuthApi client;

        @Override
        public OtpCheckResponse accept(OtpCheckRequest request) {
            Result<OtpCheckResponse> result = client.otpCheck(request);

            if (!result.isStatus() || result.getData() == null) {
                return new OtpCheckResponse();
            }

            return result.getData();
        }
    }
}
