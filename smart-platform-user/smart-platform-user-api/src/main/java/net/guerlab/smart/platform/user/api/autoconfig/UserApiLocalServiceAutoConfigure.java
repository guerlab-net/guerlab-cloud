package net.guerlab.smart.platform.user.api.autoconfig;

import lombok.AllArgsConstructor;
import net.guerlab.smart.platform.commons.exception.UserInvalidException;
import net.guerlab.smart.platform.commons.util.BeanConvertUtils;
import net.guerlab.smart.platform.user.api.UserApi;
import net.guerlab.smart.platform.user.core.domain.PositionDataDTO;
import net.guerlab.smart.platform.user.core.domain.UserDTO;
import net.guerlab.smart.platform.user.core.domain.UserModifyDTO;
import net.guerlab.smart.platform.user.core.exception.NeedPasswordException;
import net.guerlab.smart.platform.user.core.searchparams.UserSearchParams;
import net.guerlab.smart.platform.user.core.utils.PositionUtils;
import net.guerlab.smart.platform.user.service.entity.User;
import net.guerlab.smart.platform.user.service.service.PositionGetHandler;
import net.guerlab.smart.platform.user.service.service.PositionService;
import net.guerlab.smart.platform.user.service.service.UserService;
import net.guerlab.web.result.ListObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.*;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author guer
 */
@Configuration
@Conditional(UserApiLocalServiceAutoConfigure.WrapperCondition.class)
public class UserApiLocalServiceAutoConfigure {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Bean
    @ConditionalOnBean(UserService.class)
    public UserApi userApiLocalServiceWrapper(UserService service, PositionGetHandler positionGetHandler,
            PositionService positionService) {
        return new UserApiLocalServiceWrapper(service, positionGetHandler, positionService);
    }

    @SuppressWarnings("WeakerAccess")
    static class WrapperCondition implements Condition {

        @Override
        public boolean matches(@NonNull ConditionContext context, @NonNull AnnotatedTypeMetadata metadata) {
            try {
                ClassLoader classLoader = WrapperCondition.class.getClassLoader();
                String packageName = "net.guerlab.smart.platform.user.service.service.";
                return classLoader.loadClass(packageName + "UserService") != null
                        && classLoader.loadClass(packageName + "PositionGetHandler") != null
                        && classLoader.loadClass(packageName + "PositionService") != null;
            } catch (Exception e) {
                return false;
            }
        }
    }

    @AllArgsConstructor
    private static class UserApiLocalServiceWrapper implements UserApi {

        private UserService service;

        private PositionGetHandler positionGetHandler;

        private PositionService positionService;

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

        @Override
        public UserDTO add(UserModifyDTO dto) {
            String password = StringUtils.trimToNull(dto.getPassword());

            if (password == null) {
                throw new NeedPasswordException();
            }

            User user = new User();

            BeanUtils.copyProperties(dto, user);
            user.setAdmin(false);

            service.insertSelective(user);

            return user.toDTO();
        }

        @Override
        public List<String> permissionKeys(Long userId) {
            return new ArrayList<>(service.getPermissionKeys(userId));
        }

        @Override
        public List<PositionDataDTO> getPosition(Long userId) {
            return positionGetHandler.getPosition(userId);
        }

        @Override
        public Set<String> getPositionKeys(Long userId) {
            return PositionUtils.getKeys(positionService.findByUserId(userId));
        }
    }
}
