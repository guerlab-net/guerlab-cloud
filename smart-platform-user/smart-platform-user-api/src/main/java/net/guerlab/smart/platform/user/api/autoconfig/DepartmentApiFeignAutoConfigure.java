package net.guerlab.smart.platform.user.api.autoconfig;

import lombok.AllArgsConstructor;
import net.guerlab.smart.platform.user.api.DepartmentApi;
import net.guerlab.smart.platform.user.api.feign.FeignDepartmentApi;
import net.guerlab.smart.platform.user.core.domain.DepartmentDTO;
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
@AutoConfigureAfter(DepartmentApiLocalServiceAutoConfigure.class)
public class DepartmentApiFeignAutoConfigure {

    @Bean
    @ConditionalOnMissingBean(DepartmentApi.class)
    public DepartmentApi departmentApiFeignWrapper(FeignDepartmentApi api) {
        return new DepartmentApiFeignWrapper(api);
    }

    @AllArgsConstructor
    private static class DepartmentApiFeignWrapper implements DepartmentApi {

        private FeignDepartmentApi api;

        @Override
        public DepartmentDTO findOne(Long departmentId) {
            return Optional.ofNullable(api.findOne(departmentId).getData())
                    .orElseThrow(DepartmentInvalidException::new);
        }

        @Override
        public ListObject<DepartmentDTO> findList(DepartmentSearchParams searchParams) {
            Map<String, Object> params = new HashMap<>(8);
            SearchParamsUtils.handler(searchParams, params);
            return Optional.ofNullable(api.findList(params).getData()).orElse(ListObject.empty());
        }

        @Override
        public List<DepartmentDTO> findAll(DepartmentSearchParams searchParams) {
            Map<String, Object> params = new HashMap<>(8);
            SearchParamsUtils.handler(searchParams, params);
            return Optional.ofNullable(api.findAll(params).getData()).orElse(Collections.emptyList());
        }
    }

}
