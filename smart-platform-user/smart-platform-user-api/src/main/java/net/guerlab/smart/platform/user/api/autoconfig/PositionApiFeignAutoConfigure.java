package net.guerlab.smart.platform.user.api.autoconfig;

import lombok.AllArgsConstructor;
import net.guerlab.smart.platform.user.api.PositionApi;
import net.guerlab.smart.platform.user.api.feign.FeignPositionApi;
import net.guerlab.smart.platform.user.core.domain.PositionDTO;
import net.guerlab.smart.platform.user.core.exception.PositionInvalidException;
import net.guerlab.smart.platform.user.core.searchparams.PositionSearchParams;
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
@AutoConfigureAfter(PositionApiLocalServiceAutoConfigure.class)
public class PositionApiFeignAutoConfigure {

    @Bean
    @ConditionalOnMissingBean(PositionApi.class)
    public PositionApi positionApiFeignWrapper(FeignPositionApi api) {
        return new PositionApiFeignWrapper(api);
    }

    @AllArgsConstructor
    private static class PositionApiFeignWrapper implements PositionApi {

        private FeignPositionApi api;

        @Override
        public PositionDTO findOne(Long positionId) {
            return Optional.ofNullable(api.findOne(positionId).getData()).orElseThrow(PositionInvalidException::new);
        }

        @Override
        public ListObject<PositionDTO> findList(PositionSearchParams searchParams) {
            Map<String, Object> params = new HashMap<>(8);
            SearchParamsUtils.handler(searchParams, params);
            return Optional.ofNullable(api.findList(params).getData()).orElse(ListObject.empty());
        }

        @Override
        public List<PositionDTO> findAll(PositionSearchParams searchParams) {
            Map<String, Object> params = new HashMap<>(8);
            SearchParamsUtils.handler(searchParams, params);
            return Optional.ofNullable(api.findAll(params).getData()).orElse(Collections.emptyList());
        }
    }

}
