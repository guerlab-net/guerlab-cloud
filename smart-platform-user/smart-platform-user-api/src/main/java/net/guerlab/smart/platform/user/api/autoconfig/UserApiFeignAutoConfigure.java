package net.guerlab.smart.platform.user.api.autoconfig;

import lombok.AllArgsConstructor;
import net.guerlab.smart.platform.commons.exception.UserInvalidException;
import net.guerlab.smart.platform.user.api.UserApi;
import net.guerlab.smart.platform.user.api.feign.FeignUserApi;
import net.guerlab.smart.platform.user.core.domain.UserDTO;
import net.guerlab.smart.platform.user.core.searchparams.UserSearchParams;
import net.guerlab.spring.searchparams.SearchParamsUtils;
import net.guerlab.web.result.ListObject;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;

/**
 * @author guer
 */
@Configuration
@AutoConfigureAfter(UserApiLocalServiceAutoConfigure.class)
public class UserApiFeignAutoConfigure {

    @Bean
    @ConditionalOnMissingBean(UserApi.class)
    public UserApi userApiFeignWrapper(FeignUserApi api) {
        return new UserApiFeignWrapper(api);
    }

    @AllArgsConstructor
    private static class UserApiFeignWrapper implements UserApi {

        private FeignUserApi api;

        @Override
        public UserDTO findOne(Long userId) {
            return Optional.ofNullable(api.findOne(userId).getData()).orElseThrow(UserInvalidException::new);
        }

        @Override
        public ListObject<UserDTO> findList(UserSearchParams searchParams) {
            Map<String, Object> params = new HashMap<>(8);
            SearchParamsUtils.handler(searchParams, params);
            return Optional.ofNullable(api.findList(params).getData()).orElse(ListObject.empty());
        }

        @Override
        public List<UserDTO> findAll(UserSearchParams searchParams) {
            Map<String, Object> params = new HashMap<>(8);
            SearchParamsUtils.handler(searchParams, params);
            return Optional.ofNullable(api.findAll(params).getData()).orElse(Collections.emptyList());
        }
    }

}
