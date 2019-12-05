package net.guerlab.smart.platform.user.core;

/**
 * 常量
 *
 * @author guer
 */
public interface UserAuthConstants {

    /**
     * 用户id
     */
    String USER_ID = "userId";

    /**
     * 用户名
     */
    String USERNAME = "username";

    /**
     * 姓名
     */
    String NAME = "name";

    /**
     * 部门id
     */
    String DEPARTMENT_ID = "departmentId";

    /**
     * 部门名称
     */
    String DEPARTMENT_NAME = "departmentName";

    /**
     * 主管领导职位id
     */
    Long POSITION_ID_DIRECTOR = 1L;

    /**
     * 分管领导职位id
     */
    Long POSITION_ID_CHARGE = 2L;

    /**
     * 默认职位
     */
    Long POSITION_ID_DEFAULT = 3L;

    /**
     * 系统职位范围
     */
    Long SYSTEM_POSITION_ID_RANGE = 100L;

}
