package net.guerlab.smart.platform.user.auth;

import net.guerlab.smart.platform.basic.auth.AbstractContextHandler;
import net.guerlab.smart.platform.user.core.UserAuthConstants;

/**
 * 上下文处理
 *
 * @author guer
 */
@SuppressWarnings("WeakerAccess")
public final class UserContextHandler extends AbstractContextHandler {

    private UserContextHandler() {

    }

    /**
     * 获取用户ID
     *
     * @return 用户ID
     */
    public static Long getUserId() {
        return get(UserAuthConstants.USER_ID);
    }

    /**
     * 设置用户ID
     *
     * @param userId
     *         用户ID
     */
    public static void setUserId(Long userId) {
        set(UserAuthConstants.USER_ID, userId);
    }

    /**
     * 获取用户ID
     *
     * @return 用户ID
     */
    public static String getUserIdString() {
        Long userId = getUserId();
        return userId == null ? null : String.valueOf(userId);
    }

    /**
     * 获取用户名
     *
     * @return 用户名
     */
    public static String getUsername() {
        return get(UserAuthConstants.USERNAME);
    }

    /**
     * 设置用户名
     *
     * @param username
     *         用户名
     */
    public static void setUsername(String username) {
        set(UserAuthConstants.USERNAME, username);
    }

    /**
     * 获取姓名
     *
     * @return 姓名
     */
    public static String getName() {
        return get(UserAuthConstants.NAME);
    }

    /**
     * 设置姓名
     *
     * @param name
     *         姓名
     */
    public static void setName(String name) {
        set(UserAuthConstants.NAME, name);
    }

    /**
     * 获取部门id
     *
     * @return 部门id
     */
    public static Long getDepartmentId() {
        return get(UserAuthConstants.DEPARTMENT_ID);
    }

    /**
     * 设置部门id
     *
     * @param departmentId
     *         部门id
     */
    public static void setDepartmentId(Long departmentId) {
        set(UserAuthConstants.DEPARTMENT_ID, departmentId);
    }

    /**
     * 获取部门id
     *
     * @return 部门id
     */
    public static String getDepartmentIdString() {
        Long departmentId = getDepartmentId();
        return departmentId == null ? null : String.valueOf(departmentId);
    }

    /**
     * 获取部门名称
     *
     * @return 部门名称
     */
    public static String getDepartmentName() {
        return get(UserAuthConstants.DEPARTMENT_NAME);
    }

    /**
     * 设置部门名称
     *
     * @param departmentName
     *         部门名称
     */
    public static void setDepartmentName(String departmentName) {
        set(UserAuthConstants.DEPARTMENT_NAME, departmentName);
    }

}
