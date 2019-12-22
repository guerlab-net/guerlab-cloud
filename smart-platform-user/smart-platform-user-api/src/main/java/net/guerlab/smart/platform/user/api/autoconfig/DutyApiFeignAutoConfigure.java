package net.guerlab.smart.platform.user.api.autoconfig;

import lombok.AllArgsConstructor;
import net.guerlab.smart.platform.user.api.DutyApi;
import net.guerlab.smart.platform.user.api.feign.FeignDutyApi;
import net.guerlab.smart.platform.user.core.domain.DutyDTO;
import net.guerlab.smart.platform.user.core.exception.DutyInvalidException;
import net.guerlab.smart.platform.user.core.searchparams.DutySearchParams;
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
@AutoConfigureAfter(DutyApiLocalServiceAutoConfigure.class)
public class DutyApiFeignAutoConfigure {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Bean
    @ConditionalOnMissingBean(DutyApi.class)
    public DutyApi dutyApiFeignWrapper(FeignDutyApi api) {
        return new DutyApiFeignWrapper(api);
    }

    @AllArgsConstructor
    private static class DutyApiFeignWrapper implements DutyApi {

        private FeignDutyApi api;

        @Override
        public DutyDTO findOne(Long dutyId) {
            return Optional.ofNullable(api.findOne(dutyId).getData()).orElseThrow(DutyInvalidException::new);
        }

        @Override
        public ListObject<DutyDTO> findList(DutySearchParams searchParams) {
            Map<String, Object> params = new HashMap<>(8);
            SearchParamsUtils.handler(searchParams, params);
            return Optional.ofNullable(api.findList(params).getData()).orElse(ListObject.empty());
        }

        @Override
        public List<DutyDTO> findAll(DutySearchParams searchParams) {
            Map<String, Object> params = new HashMap<>(8);
            SearchParamsUtils.handler(searchParams, params);
            return Optional.ofNullable(api.findAll(params).getData()).orElse(Collections.emptyList());
        }
    }

}
