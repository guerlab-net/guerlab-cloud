package net.guerlab.smart.platform.user.auth.autoconfig;

import lombok.AllArgsConstructor;
import net.guerlab.smart.platform.user.auth.PermissionCheckApi;
import net.guerlab.smart.platform.user.auth.feign.FeignAuthApi;
import net.guerlab.smart.platform.user.core.entity.PermissionCheckRequest;
import net.guerlab.smart.platform.user.core.entity.PermissionCheckResponse;
import net.guerlab.web.result.Result;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author guer
 */
@Configuration
@AutoConfigureAfter(PermissionCheckApiLocalServiceAutoConfigure.class)
public class PermissionCheckApiFeignAutoConfigure {

    @Bean
    @ConditionalOnMissingBean(PermissionCheckApi.class)
    public PermissionCheckApi permissionCheckApiFeignWrapper(FeignAuthApi client) {
        return new PermissionCheckApiFeignWrapper(client);
    }

    @AllArgsConstructor
    private static class PermissionCheckApiFeignWrapper implements PermissionCheckApi {

        private FeignAuthApi client;

        @Override
        public PermissionCheckResponse accept(PermissionCheckRequest request) {
            Result<PermissionCheckResponse> result = client.permissionCheck(request);

            if (!result.isStatus() || result.getData() == null) {
                return new PermissionCheckResponse();
            }

            return result.getData();
        }
    }
}
