package net.guerlab.smart.platform.user.core.domain;

/**
 * 职位信息接口
 *
 * @author guer
 */
public interface IPosition {

    /**
     * 获取部门ID
     *
     * @return 部门ID
     */
    Long getDepartmentId();

    /**
     * 获取职务ID
     *
     * @return 职务ID
     */
    Long getDutyId();
}
