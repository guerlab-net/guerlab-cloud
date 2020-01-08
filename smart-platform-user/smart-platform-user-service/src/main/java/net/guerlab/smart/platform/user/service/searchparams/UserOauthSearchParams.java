package net.guerlab.smart.platform.user.service.searchparams;

import lombok.Getter;
import lombok.Setter;
import net.guerlab.spring.searchparams.AbstractSearchParams;

/**
 * 用户oauth信息
 *
 * @author guer
 */
@Setter
@Getter
public class UserOauthSearchParams extends AbstractSearchParams {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 类型
     */
    private String type;

    /**
     * 第三方ID
     */
    private String thirdPartyId;
}
