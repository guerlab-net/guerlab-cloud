package net.guerlab.smart.platform.user.api.autoconfig;

import lombok.AllArgsConstructor;
import net.guerlab.smart.platform.user.api.SimpleDepartmentApi;
import net.guerlab.smart.platform.user.api.feign.FeignSimpleDepartmentApi;
import net.guerlab.smart.platform.user.core.domain.SimpleDepartmentDTO;
import net.guerlab.smart.platform.user.core.exception.DepartmentInvalidException;
import net.guerlab.smart.platform.user.core.searchparams.DepartmentSearchParams;
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
@AutoConfigureAfter(SimpleDepartmentApiLocalServiceAutoConfigure.class)
public class SimpleDepartmentApiFeignAutoConfigure {

    @Bean
    @ConditionalOnMissingBean(SimpleDepartmentApi.class)
    public SimpleDepartmentApi simpleDepartmentApiFeignWrapper(FeignSimpleDepartmentApi api) {
        return new SimpleDepartmentApiFeignWrapper(api);
    }

    @AllArgsConstructor
    private static class SimpleDepartmentApiFeignWrapper implements SimpleDepartmentApi {

        private FeignSimpleDepartmentApi api;

        @Override
        public SimpleDepartmentDTO findOne(Long departmentId) {
            return Optional.ofNullable(api.findOne(departmentId).getData())
                    .orElseThrow(DepartmentInvalidException::new);
        }

        @Override
        public ListObject<SimpleDepartmentDTO> findList(DepartmentSearchParams searchParams) {
            Map<String, Object> params = new HashMap<>(8);
            SearchParamsUtils.handler(searchParams, params);
            return Optional.ofNullable(api.findList(params).getData()).orElse(ListObject.empty());
        }

        @Override
        public List<SimpleDepartmentDTO> findAll(DepartmentSearchParams searchParams) {
            Map<String, Object> params = new HashMap<>(8);
            SearchParamsUtils.handler(searchParams, params);
            return Optional.ofNullable(api.findAll(params).getData()).orElse(Collections.emptyList());
        }
    }

}
