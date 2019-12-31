package net.guerlab.smart.platform.user.core.searchparams;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import net.guerlab.smart.platform.commons.enums.Gender;
import net.guerlab.spring.searchparams.AbstractSearchParams;
import net.guerlab.spring.searchparams.SearchModel;
import net.guerlab.spring.searchparams.SearchModelType;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 * 用户搜索参数
 *
 * @author guer
 */
@Getter
@Setter
@ApiModel("用户搜索参数")
public class UserSearchParams extends AbstractSearchParams {

    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    private Long userId;

    /**
     * 用户ID列表
     */
    @ApiModelProperty("用户ID列表")
    @Column(name = "userId")
    @SearchModel(SearchModelType.IN)
    private Collection<Long> userIds;

    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    private String username;

    /**
     * 用户名关键字
     */
    @ApiModelProperty("用户名关键字")
    @Column(name = "username")
    @SearchModel(SearchModelType.LIKE)
    private String usernameLike;

    /**
     * 姓名
     */
    @ApiModelProperty("姓名")
    private String name;

    /**
     * 姓名关键字
     */
    @ApiModelProperty("姓名关键字")
    @Column(name = "name")
    @SearchModel(SearchModelType.LIKE)
    private String nameLike;

    /**
     * 部门ID
     */
    @ApiModelProperty("部门ID")
    private Long departmentId;

    /**
     * 部门ID列表
     */
    @ApiModelProperty("部门ID列表")
    @Column(name = "departmentId")
    @SearchModel(SearchModelType.IN)
    private Collection<Long> departmentIds;

    /**
     * 主部门ID
     */
    @ApiModelProperty("主部门ID")
    @Column(name = "departmentId")
    private Long mainDepartmentId;

    /**
     * 主部门ID列表
     */
    @ApiModelProperty("主部门ID列表")
    @Column(name = "departmentId")
    @SearchModel(SearchModelType.IN)
    private Collection<Long> mainDepartmentIds;

    /**
     * 主职务ID
     */
    @ApiModelProperty("主职务ID")
    private Long mainDutyId;

    /**
     * 主职务ID列表
     */
    @ApiModelProperty("主职务ID列表")
    @Column(name = "mainDutyId")
    @SearchModel(SearchModelType.IN)
    private Collection<Long> mainDutyIds;

    /**
     * 性别
     */
    @ApiModelProperty("性别")
    private Gender gender;

    /**
     * 电子邮箱
     */
    @ApiModelProperty("电子邮箱")
    private String email;

    /**
     * 电话
     */
    @ApiModelProperty("电话")
    private String phone;

    /**
     * 注册时间开始范围
     */
    @ApiModelProperty("注册时间开始范围")
    @Column(name = "registrationTime")
    @SearchModel(SearchModelType.GREATER_THAN_OR_EQUAL_TO)
    private LocalDateTime registrationTimeStartWith;

    /**
     * 注册时间结束范围
     */
    @ApiModelProperty("注册时间结束范围")
    @Column(name = "registrationTime")
    @SearchModel(SearchModelType.LESS_THAN_OR_EQUAL_TO)
    private LocalDateTime registrationTimeEndWith;

    /**
     * 最后登录时间开始范围
     */
    @ApiModelProperty("最后登录时间开始范围")
    @Column(name = "lastLoginTime")
    @SearchModel(SearchModelType.GREATER_THAN_OR_EQUAL_TO)
    private LocalDateTime lastLoginTimeStartWith;

    /**
     * 最后登录时间结束范围
     */
    @ApiModelProperty("最后登录时间结束范围")
    @Column(name = "lastLoginTime")
    @SearchModel(SearchModelType.LESS_THAN_OR_EQUAL_TO)
    private LocalDateTime lastLoginTimeEndWith;

    /**
     * 帐号启用
     */
    @ApiModelProperty("帐号启用")
    private Boolean enabled;

    /**
     * 职务ID
     */
    @ApiModelProperty("职务ID")
    @SearchModel(SearchModelType.IGNORE)
    private Long dutyId;
}
