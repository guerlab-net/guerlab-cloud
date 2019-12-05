package net.guerlab.smart.platform.user.auth.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import net.guerlab.smart.platform.basic.auth.utils.AbstractJwtHelper;
import net.guerlab.smart.platform.user.auth.properties.UserJwtProperties;
import net.guerlab.smart.platform.user.core.UserAuthConstants;
import net.guerlab.smart.platform.user.core.entity.IJwtInfo;
import net.guerlab.smart.platform.user.core.entity.JwtInfo;

/**
 * jwt助手
 *
 * @author guer
 */
public class UserJwtHelper extends AbstractJwtHelper<IJwtInfo, UserJwtProperties> {

    /**
     * 签名前缀
     */
    public static final String PREFIX = "USER_JWT";

    @Override
    protected void generateToken0(JwtBuilder builder, IJwtInfo user) {
        builder.setSubject(user.getUsername());
        builder.claim(UserAuthConstants.USER_ID, user.getUserId());
        builder.claim(UserAuthConstants.NAME, user.getName());
        builder.claim(UserAuthConstants.DEPARTMENT_ID, user.getDepartmentId());
        builder.claim(UserAuthConstants.DEPARTMENT_NAME, user.getDepartmentName());
    }

    @Override
    protected IJwtInfo parse0(Claims body) {
        Long userId = Long.parseLong(getObjectValue(body.get(UserAuthConstants.USER_ID)));
        String username = body.getSubject();
        String name = getObjectValue(body.get(UserAuthConstants.NAME));
        Long departmentId = Long.parseLong(getObjectValue(body.get(UserAuthConstants.DEPARTMENT_ID)));
        String departmentName = getObjectValue(body.get(UserAuthConstants.DEPARTMENT_NAME));

        return new JwtInfo(userId, username, name, departmentId, departmentName);
    }

    @Override
    protected String getPrefix() {
        return PREFIX;
    }
}
