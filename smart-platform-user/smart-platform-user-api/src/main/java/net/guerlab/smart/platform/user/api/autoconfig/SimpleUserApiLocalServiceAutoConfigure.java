package net.guerlab.smart.platform.user.api.autoconfig;

import lombok.AllArgsConstructor;
import net.guerlab.smart.platform.commons.exception.UserInvalidException;
import net.guerlab.smart.platform.commons.util.BeanConvertUtils;
import net.guerlab.smart.platform.user.api.SimpleUserApi;
import net.guerlab.smart.platform.user.core.domain.SimpleUserDTO;
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
@Conditional(SimpleUserApiLocalServiceAutoConfigure.WrapperCondition.class)
public class SimpleUserApiLocalServiceAutoConfigure {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Bean
    @ConditionalOnBean(UserService.class)
    public SimpleUserApi simpleUserApiLocalServiceWrapper(UserService service) {
        return new SimpleUserApiLocalServiceWrapper(service);
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
    private static class SimpleUserApiLocalServiceWrapper implements SimpleUserApi {

        private UserService service;

        @Override
        public SimpleUserDTO findOne(Long userId) {
            return BeanConvertUtils.toObject(service.selectByIdOptional(userId).orElseThrow(UserInvalidException::new),
                    SimpleUserDTO.class);
        }

        @Override
        public ListObject<SimpleUserDTO> findList(UserSearchParams searchParams) {
            return BeanConvertUtils.toListObject(service.selectPage(searchParams), SimpleUserDTO.class);
        }

        @Override
        public List<SimpleUserDTO> findAll(UserSearchParams searchParams) {
            return BeanConvertUtils.toList(service.selectAll(searchParams), SimpleUserDTO.class);
        }
    }
}
