package net.guerlab.smart.platform.user.service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.guerlab.smart.platform.commons.entity.BaseEntity;
import net.guerlab.smart.platform.commons.enums.Gender;
import net.guerlab.smart.platform.user.core.domain.UserDTO;
import net.guerlab.smart.platform.user.core.entity.IJwtInfo;
import net.guerlab.spring.commons.dto.DefaultConvertDTO;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.time.LocalDateTime;

/**
 * 用户
 *
 * @author guer
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "user_user")
public class User extends BaseEntity implements DefaultConvertDTO<UserDTO>, IJwtInfo {

    /**
     * 用户id
     */
    @Id
    private Long userId;

    /**
     * 用户名
     */
    @Column(name = "username", nullable = false, unique = true, updatable = false)
    private String username;

    /**
     * 密钥
     */
    @JsonIgnore
    @Column(name = "password", nullable = false)
    private String password;

    /**
     * 姓名
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * 旧部门ID
     */
    @Transient
    private Long oldDepartmentId;

    /**
     * 部门ID
     */
    @Column(name = "departmentId", nullable = false)
    private Long departmentId;

    /**
     * 部门名称
     */
    @Column(name = "departmentName", nullable = false)
    private String departmentName;

    /**
     * 旧主职务ID
     */
    @Transient
    private Long oldMainDutyId;

    /**
     * 主职务ID
     */
    @Column(name = "mainDutyId")
    private Long mainDutyId;

    /**
     * 主职务名称
     */
    @Column(name = "mainDutyName")
    private String mainDutyName;

    /**
     * 性别
     */
    @Column(name = "gender", nullable = false)
    private Gender gender;

    /**
     * 头像
     */
    @Column(name = "avatar", nullable = false)
    private String avatar;

    /**
     * 电子邮箱
     */
    @Column(name = "email", nullable = false)
    private String email;

    /**
     * 电话
     */
    @Column(name = "phone", nullable = false)
    private String phone;

    /**
     * 注册时间
     */
    @Column(name = "registrationTime", nullable = false, updatable = false)
    private LocalDateTime registrationTime;

    /**
     * 最后登录时间
     */
    @Column(name = "lastLoginTime", nullable = false)
    private LocalDateTime lastLoginTime;

    /**
     * 帐号启用
     */
    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    /**
     * 管理员账号标志
     */
    @Column(name = "admin", nullable = false)
    private Boolean admin;

    /**
     * 更新时间
     */
    @Column(name = "updateTime", nullable = false)
    private LocalDateTime updateTime;

    /**
     * 启用双因子认证标志
     */
    @Column(name = "enableTwoFactorAuthentication", nullable = false)
    private Boolean enableTwoFactorAuthentication;

    /**
     * 双因子认证token
     */
    @Column(name = "twoFactorAuthenticationToken", nullable = false)
    private String twoFactorAuthenticationToken;
}
