package net.guerlab.smart.platform.user.auth.autoconfig;

import lombok.AllArgsConstructor;
import net.guerlab.smart.platform.user.auth.OtpCheckApi;
import net.guerlab.smart.platform.user.core.entity.OtpCheckRequest;
import net.guerlab.smart.platform.user.core.entity.OtpCheckResponse;
import net.guerlab.smart.platform.user.server.service.OtpCheckService;
import org.springframework.context.annotation.*;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.lang.NonNull;

/**
 * @author guer
 */
@Configuration
@Conditional(OtpCheckApiLocalServiceAutoConfigure.WrapperCondition.class)
public class OtpCheckApiLocalServiceAutoConfigure {

    @Bean
    public OtpCheckApi otpCheckApiLocalServiceWrapper(OtpCheckService service) {
        return new OtpCheckApiLocalServiceWrapper(service);
    }

    @SuppressWarnings("WeakerAccess")
    static class WrapperCondition implements Condition {

        @Override
        public boolean matches(@NonNull ConditionContext context, @NonNull AnnotatedTypeMetadata metadata) {
            try {
                return WrapperCondition.class.getClassLoader()
                        .loadClass("net.guerlab.smart.platform.user.server.service.OtpCheckService") != null;
            } catch (Exception e) {
                return false;
            }
        }
    }

    @AllArgsConstructor
    private static class OtpCheckApiLocalServiceWrapper implements OtpCheckApi {

        private OtpCheckService service;

        @Override
        public OtpCheckResponse accept(OtpCheckRequest request) {
            return service.otpCheck(request);
        }
    }
}
