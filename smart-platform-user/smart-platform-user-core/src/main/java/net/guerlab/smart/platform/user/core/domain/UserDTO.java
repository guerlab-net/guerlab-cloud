package net.guerlab.smart.platform.user.core.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.guerlab.smart.platform.commons.enums.Gender;

import java.time.LocalDateTime;

/**
 * 用户
 *
 * @author guer
 */
@Data
@ApiModel("用户")
public class UserDTO {

    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    private Long userId;

    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    private String username;

    /**
     * 姓名
     */
    @ApiModelProperty("姓名")
    private String name;

    /**
     * 部门ID
     */
    @ApiModelProperty("部门ID")
    private Long departmentId;

    /**
     * 部门名称
     */
    @ApiModelProperty("部门名称")
    private String departmentName;

    /**
     * 性别
     */
    @ApiModelProperty("性别")
    private Gender gender;

    /**
     * 头像
     */
    @ApiModelProperty("头像")
    private String avatar;

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
     * 注册时间
     */
    @ApiModelProperty("注册时间")
    private LocalDateTime registrationTime;

    /**
     * 最后登录时间
     */
    @ApiModelProperty("最后登录时间")
    private LocalDateTime lastLoginTime;

    /**
     * 帐号启用
     */
    @ApiModelProperty("帐号启用")
    private Boolean enabled;

    /**
     * 管理员账号标志
     */
    @ApiModelProperty("管理员账号标志")
    private Boolean admin;

    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;

    /**
     * 启用双因子认证标志
     */
    @ApiModelProperty("启用双因子认证标志")
    private Boolean enableTwoFactorAuthentication;
}
