package net.guerlab.smart.platform.user.api.autoconfig;

import lombok.AllArgsConstructor;
import net.guerlab.smart.platform.user.api.DepartmentTypeApi;
import net.guerlab.smart.platform.user.api.feign.FeignDepartmentTypeApi;
import net.guerlab.smart.platform.user.core.domain.DepartmentTypeDTO;
import net.guerlab.smart.platform.user.core.exception.DepartmentTypeInvalidException;
import net.guerlab.smart.platform.user.core.searchparams.DepartmentTypeSearchParams;
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
@AutoConfigureAfter(DepartmentTypeApiLocalServiceAutoConfigure.class)
public class DepartmentTypeApiFeignAutoConfigure {

    @Bean
    @ConditionalOnMissingBean(DepartmentTypeApi.class)
    public DepartmentTypeApi departmentTypeApiFeignWrapper(FeignDepartmentTypeApi api) {
        return new DepartmentTypeApiFeignWrapper(api);
    }

    @AllArgsConstructor
    private static class DepartmentTypeApiFeignWrapper implements DepartmentTypeApi {

        private FeignDepartmentTypeApi api;

        @Override
        public DepartmentTypeDTO findOne(String departmentTypeKey) {
            return Optional.ofNullable(api.findOne(departmentTypeKey).getData())
                    .orElseThrow(DepartmentTypeInvalidException::new);
        }

        @Override
        public ListObject<DepartmentTypeDTO> findList(DepartmentTypeSearchParams searchParams) {
            Map<String, Object> params = new HashMap<>(8);
            SearchParamsUtils.handler(searchParams, params);
            return Optional.ofNullable(api.findList(params).getData()).orElse(ListObject.empty());
        }

        @Override
        public List<DepartmentTypeDTO> findAll(DepartmentTypeSearchParams searchParams) {
            Map<String, Object> params = new HashMap<>(8);
            SearchParamsUtils.handler(searchParams, params);
            return Optional.ofNullable(api.findAll(params).getData()).orElse(Collections.emptyList());
        }
    }

}
