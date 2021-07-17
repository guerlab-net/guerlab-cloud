package net.guerlab.cloud.api.endpoints;

import lombok.Data;

/**
 * feign客户端注解信息
 *
 * @author guer
 */
@Data
public class FeignClientAnnotationInfo {

    /**
     * feign注解上的name
     */
    private String name;

    /**
     * feign注解上的contextId
     */
    private String contextId;

    /**
     * feign注解上的url
     */
    private String url;

    /**
     * feign注解上的decode404
     */
    private boolean decode404;

    /**
     * feign注解上的path
     */
    private String path;

    /**
     * feign注解上的primary
     */
    private boolean primary;
}
