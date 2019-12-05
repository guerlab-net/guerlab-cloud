package net.guerlab.smart.platform.user.core.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * JWT信息
 *
 * @author guer
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtInfo implements IJwtInfo {

    private Long userId;

    private String username;

    private String name;

    private Long departmentId;

    private String departmentName;
}
