package net.guerlab.smart.platform.user.core.entity;

/**
 * JWT信息接口
 *
 * @author guer
 */
public interface IJwtInfo {

    /**
     * 获取用户ID
     *
     * @return 用户ID
     */
    Long getUserId();

    /**
     * 获取用户名
     *
     * @return 用户名
     */
    String getUsername();

    /**
     * 获取姓名
     *
     * @return 姓名
     */
    String getName();

    /**
     * 获取部门ID
     *
     * @return 部门ID
     */
    Long getDepartmentId();

    /**
     * 获取部门名称
     *
     * @return 部门名称
     */
    String getDepartmentName();
}
