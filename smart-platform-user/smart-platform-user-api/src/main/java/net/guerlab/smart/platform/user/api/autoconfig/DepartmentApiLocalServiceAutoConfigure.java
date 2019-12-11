package net.guerlab.smart.platform.user.api.autoconfig;

import lombok.AllArgsConstructor;
import net.guerlab.smart.platform.commons.util.BeanConvertUtils;
import net.guerlab.smart.platform.user.api.DepartmentApi;
import net.guerlab.smart.platform.user.core.domain.DepartmentDTO;
import net.guerlab.smart.platform.user.core.exception.DepartmentInvalidException;
import net.guerlab.smart.platform.user.core.searchparams.DepartmentSearchParams;
import net.guerlab.smart.platform.user.service.service.DepartmentService;
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
@Conditional(DepartmentApiLocalServiceAutoConfigure.WrapperCondition.class)
public class DepartmentApiLocalServiceAutoConfigure {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Bean
    @ConditionalOnBean(DepartmentService.class)
    public DepartmentApi departmentApiLocalServiceWrapper(DepartmentService service) {
        return new DepartmentApiLocalServiceWrapper(service);
    }

    @SuppressWarnings("WeakerAccess")
    static class WrapperCondition implements Condition {

        @Override
        public boolean matches(@NonNull ConditionContext context, @NonNull AnnotatedTypeMetadata metadata) {
            try {
                return WrapperCondition.class.getClassLoader()
                        .loadClass("net.guerlab.smart.platform.user.service.service.DepartmentService") != null;
            } catch (Exception e) {
                return false;
            }
        }
    }

    @AllArgsConstructor
    private static class DepartmentApiLocalServiceWrapper implements DepartmentApi {

        private DepartmentService service;

        @Override
        public DepartmentDTO findOne(Long departmentId) {
            return service.selectByIdOptional(departmentId).orElseThrow(DepartmentInvalidException::new).toDTO();
        }

        @Override
        public ListObject<DepartmentDTO> findList(DepartmentSearchParams searchParams) {
            return BeanConvertUtils.toListObject(service.selectPage(searchParams));
        }

        @Override
        public List<DepartmentDTO> findAll(DepartmentSearchParams searchParams) {
            return BeanConvertUtils.toList(service.selectAll(searchParams));
        }
    }

}
