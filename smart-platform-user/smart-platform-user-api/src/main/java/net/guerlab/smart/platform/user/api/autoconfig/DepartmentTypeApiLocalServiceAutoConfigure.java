package net.guerlab.smart.platform.user.api.autoconfig;

import lombok.AllArgsConstructor;
import net.guerlab.smart.platform.commons.util.BeanConvertUtils;
import net.guerlab.smart.platform.user.api.DepartmentTypeApi;
import net.guerlab.smart.platform.user.core.domain.DepartmentTypeDTO;
import net.guerlab.smart.platform.user.core.exception.DepartmentTypeInvalidException;
import net.guerlab.smart.platform.user.core.searchparams.DepartmentTypeSearchParams;
import net.guerlab.smart.platform.user.service.service.DepartmentTypeService;
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
@Conditional(DepartmentTypeApiLocalServiceAutoConfigure.WrapperCondition.class)
public class DepartmentTypeApiLocalServiceAutoConfigure {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Bean
    @ConditionalOnBean(DepartmentTypeService.class)
    public DepartmentTypeApi departmentTypeApiLocalServiceWrapper(DepartmentTypeService service) {
        return new DepartmentTypeApiLocalServiceWrapper(service);
    }

    @SuppressWarnings("WeakerAccess")
    static class WrapperCondition implements Condition {

        @Override
        public boolean matches(@NonNull ConditionContext context, @NonNull AnnotatedTypeMetadata metadata) {
            try {
                return WrapperCondition.class.getClassLoader()
                        .loadClass("net.guerlab.smart.platform.user.service.service.DepartmentTypeService") != null;
            } catch (Exception e) {
                return false;
            }
        }
    }

    @AllArgsConstructor
    private static class DepartmentTypeApiLocalServiceWrapper implements DepartmentTypeApi {

        private DepartmentTypeService service;

        @Override
        public DepartmentTypeDTO findOne(String departmentTypeKey) {
            return service.selectByIdOptional(departmentTypeKey).orElseThrow(DepartmentTypeInvalidException::new)
                    .toDTO();
        }

        @Override
        public ListObject<DepartmentTypeDTO> findList(DepartmentTypeSearchParams searchParams) {
            return BeanConvertUtils.toListObject(service.selectPage(searchParams));
        }

        @Override
        public List<DepartmentTypeDTO> findAll(DepartmentTypeSearchParams searchParams) {
            return BeanConvertUtils.toList(service.selectAll(searchParams));
        }
    }
}
