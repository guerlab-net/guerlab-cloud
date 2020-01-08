package net.guerlab.smart.platform.user.service.entity;

import lombok.Data;
import net.guerlab.smart.platform.user.core.entity.UserOauthDTO;
import net.guerlab.spring.commons.dto.DefaultConvertDTO;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户oauth信息
 *
 * @author guer
 */
@Data
@Table(name = "user_oauth")
public class UserOauth implements DefaultConvertDTO<UserOauthDTO> {

    /**
     * 用户ID
     */
    @Id
    private Long userId;

    /**
     * 类型
     */
    @Id
    private String type;

    /**
     * 第三方ID
     */
    @Id
    private String thirdPartyId;
}
