package net.guerlab.smart.platform.user.api.autoconfig;

import lombok.AllArgsConstructor;
import net.guerlab.smart.platform.commons.util.BeanConvertUtils;
import net.guerlab.smart.platform.user.api.SimpleDepartmentApi;
import net.guerlab.smart.platform.user.core.domain.SimpleDepartmentDTO;
import net.guerlab.smart.platform.user.core.exception.DepartmentInvalidException;
import net.guerlab.smart.platform.user.core.searchparams.DepartmentSearchParams;
import net.guerlab.smart.platform.user.server.service.DepartmentService;
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
@Conditional(SimpleDepartmentApiLocalServiceAutoConfigure.WrapperCondition.class)
public class SimpleDepartmentApiLocalServiceAutoConfigure {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Bean
    @ConditionalOnBean(DepartmentService.class)
    public SimpleDepartmentApi simpleDepartmentApiLocalServiceWrapper(DepartmentService service) {
        return new SimpleDepartmentApiLocalServiceWrapper(service);
    }

    @SuppressWarnings("WeakerAccess")
    static class WrapperCondition implements Condition {

        @Override
        public boolean matches(@NonNull ConditionContext context, @NonNull AnnotatedTypeMetadata metadata) {
            try {
                return WrapperCondition.class.getClassLoader()
                        .loadClass("net.guerlab.smart.platform.user.server.service.DepartmentService") != null;
            } catch (Exception e) {
                return false;
            }
        }
    }

    @AllArgsConstructor
    private static class SimpleDepartmentApiLocalServiceWrapper implements SimpleDepartmentApi {

        private DepartmentService service;

        @Override
        public SimpleDepartmentDTO findOne(Long departmentId) {
            return BeanConvertUtils
                    .toObject(service.selectByIdOptional(departmentId).orElseThrow(DepartmentInvalidException::new),
                            SimpleDepartmentDTO.class);
        }

        @Override
        public ListObject<SimpleDepartmentDTO> findList(DepartmentSearchParams searchParams) {
            return BeanConvertUtils.toListObject(service.selectPage(searchParams), SimpleDepartmentDTO.class);
        }

        @Override
        public List<SimpleDepartmentDTO> findAll(DepartmentSearchParams searchParams) {
            return BeanConvertUtils.toList(service.selectAll(searchParams), SimpleDepartmentDTO.class);
        }
    }

}
