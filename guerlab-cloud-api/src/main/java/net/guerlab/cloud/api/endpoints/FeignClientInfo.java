package net.guerlab.cloud.api.endpoints;

import lombok.Data;

/**
 * feign客户端实例信息
 *
 * @author guer
 */
@Data
public class FeignClientInfo {

    /**
     * bean名称
     */
    private String beanName;

    /**
     * 类路径
     */
    private String classPath;

    /**
     * 解析后的请求路径
     */
    private String url;

    /**
     * 注解信息
     */
    private FeignClientAnnotationInfo annotation;
}
