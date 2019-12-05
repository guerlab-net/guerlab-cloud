package net.guerlab.smart.platform.user.api.autoconfig;

import lombok.AllArgsConstructor;
import net.guerlab.smart.platform.commons.exception.UserInvalidException;
import net.guerlab.smart.platform.commons.util.BeanConvertUtils;
import net.guerlab.smart.platform.user.api.UserApi;
import net.guerlab.smart.platform.user.core.domain.UserDTO;
import net.guerlab.smart.platform.user.core.searchparams.UserSearchParams;
import net.guerlab.smart.platform.user.server.service.UserService;
import net.guerlab.web.result.ListObject;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.*;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * @author guer
 */
@Configuration
@Conditional(UserApiLocalServiceAutoConfigure.WrapperCondition.class)
public class UserApiLocalServiceAutoConfigure {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Bean
    @ConditionalOnBean(UserService.class)
    public UserApi userApiLocalServiceWrapper(UserService service) {
        return new UserApiLocalServiceWrapper(service);
    }

    @SuppressWarnings("WeakerAccess")
    static class WrapperCondition implements Condition {

        @Override
        public boolean matches(@NonNull ConditionContext context, @NonNull AnnotatedTypeMetadata metadata) {
            try {
                return WrapperCondition.class.getClassLoader()
                        .loadClass("net.guerlab.smart.platform.user.server.service.UserService") != null;
            } catch (Exception e) {
                return false;
            }
        }
    }

    @AllArgsConstructor
    private static class UserApiLocalServiceWrapper implements UserApi {

        private UserService service;

        @Override
        public UserDTO findOne(Long userId) {
            return service.selectByIdOptional(userId).orElseThrow(UserInvalidException::new).toDTO();
        }

        @Override
        public ListObject<UserDTO> findList(UserSearchParams searchParams) {
            return BeanConvertUtils.toListObject(service.selectPage(searchParams));
        }

        @Override
        public List<UserDTO> findAll(UserSearchParams searchParams) {
            return BeanConvertUtils.toList(service.selectAll(searchParams));
        }
    }
}
