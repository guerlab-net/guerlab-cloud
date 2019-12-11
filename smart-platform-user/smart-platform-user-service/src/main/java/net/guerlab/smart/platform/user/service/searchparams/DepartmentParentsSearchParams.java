package net.guerlab.smart.platform.user.service.searchparams;

import lombok.Getter;
import lombok.Setter;
import net.guerlab.spring.searchparams.AbstractSearchParams;
import net.guerlab.spring.searchparams.SearchModel;
import net.guerlab.spring.searchparams.SearchModelType;

import javax.persistence.Column;
import java.util.Collection;

/**
 * 部门上下级关系搜索参数
 *
 * @author guer
 */
@Setter
@Getter
public class DepartmentParentsSearchParams extends AbstractSearchParams {

    /**
     * 部门id列表
     */
    private Long departmentId;

    /**
     * 部门id
     */
    @Column(name = "departmentId")
    @SearchModel(SearchModelType.IN)
    private Collection<Long> departmentIds;

    /**
     * 上级id
     */
    private Long parentId;

    /**
     * 上级id列表
     */
    @Column(name = "parentId")
    @SearchModel(SearchModelType.IN)
    private Collection<Long> parentIds;
}
