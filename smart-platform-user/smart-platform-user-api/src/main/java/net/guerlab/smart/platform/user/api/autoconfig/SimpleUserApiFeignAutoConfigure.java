package net.guerlab.smart.platform.user.api.autoconfig;

import lombok.AllArgsConstructor;
import net.guerlab.smart.platform.commons.exception.UserInvalidException;
import net.guerlab.smart.platform.user.api.SimpleUserApi;
import net.guerlab.smart.platform.user.api.feign.FeignSimpleUserApi;
import net.guerlab.smart.platform.user.core.domain.SimpleUserDTO;
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
@AutoConfigureAfter(SimpleUserApiLocalServiceAutoConfigure.class)
public class SimpleUserApiFeignAutoConfigure {

    @Bean
    @ConditionalOnMissingBean(SimpleUserApi.class)
    public SimpleUserApi simpleUserApiFeignWrapper(FeignSimpleUserApi api) {
        return new SimpleUserApiFeignWrapper(api);
    }

    @AllArgsConstructor
    private static class SimpleUserApiFeignWrapper implements SimpleUserApi {

        private FeignSimpleUserApi api;

        @Override
        public SimpleUserDTO findOne(Long userId) {
            return Optional.ofNullable(api.findOne(userId).getData()).orElseThrow(UserInvalidException::new);
        }

        @Override
        public ListObject<SimpleUserDTO> findList(UserSearchParams searchParams) {
            Map<String, Object> params = new HashMap<>(8);
            SearchParamsUtils.handler(searchParams, params);
            return Optional.ofNullable(api.findList(params).getData()).orElse(ListObject.empty());
        }

        @Override
        public List<SimpleUserDTO> findAll(UserSearchParams searchParams) {
            Map<String, Object> params = new HashMap<>(8);
            SearchParamsUtils.handler(searchParams, params);
            return Optional.ofNullable(api.findAll(params).getData()).orElse(Collections.emptyList());
        }
    }

}
