package net.guerlab.smart.platform.user.api.autoconfig;

import lombok.AllArgsConstructor;
import net.guerlab.smart.platform.commons.util.BeanConvertUtils;
import net.guerlab.smart.platform.user.api.DutyApi;
import net.guerlab.smart.platform.user.core.domain.DutyDTO;
import net.guerlab.smart.platform.user.core.exception.DutyInvalidException;
import net.guerlab.smart.platform.user.core.searchparams.DutySearchParams;
import net.guerlab.smart.platform.user.service.service.DutyService;
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
@Conditional(DutyApiLocalServiceAutoConfigure.WrapperCondition.class)
public class DutyApiLocalServiceAutoConfigure {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Bean
    @ConditionalOnBean(DutyService.class)
    public DutyApi dutyApiLocalServiceWrapper(DutyService service) {
        return new DutyApiLocalServiceWrapper(service);
    }

    @SuppressWarnings("WeakerAccess")
    static class WrapperCondition implements Condition {

        @Override
        public boolean matches(@NonNull ConditionContext context, @NonNull AnnotatedTypeMetadata metadata) {
            try {
                return WrapperCondition.class.getClassLoader()
                        .loadClass("net.guerlab.smart.platform.user.service.service.DutyService") != null;
            } catch (Exception e) {
                return false;
            }
        }
    }

    @AllArgsConstructor
    private static class DutyApiLocalServiceWrapper implements DutyApi {

        private DutyService service;

        @Override
        public DutyDTO findOne(Long dutyId) {
            return service.selectByIdOptional(dutyId).orElseThrow(DutyInvalidException::new).toDTO();
        }

        @Override
        public ListObject<DutyDTO> findList(DutySearchParams searchParams) {
            return BeanConvertUtils.toListObject(service.selectPage(searchParams));
        }

        @Override
        public List<DutyDTO> findAll(DutySearchParams searchParams) {
            return BeanConvertUtils.toList(service.selectAll(searchParams));
        }
    }

}
