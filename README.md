# Guerlab Cloud

![](https://img.shields.io/maven-central/v/net.guerlab.cloud/guerlab-cloud.svg)
![](https://img.shields.io/badge/LICENSE-LGPL--3.0-brightgreen.svg)

基于java11进行开发<br>
基于Spring Cloud与Spring Cloud Alibaba的扩展框架，提供feign、授权、缓存、负载均衡、请求上下文、国际化、excel、安全、消息队列、web、openapi(swagger)、RSA等扩展<br>

## 依赖管理

```
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>net.guerlab.cloud</groupId>
            <artifactId>guerlab-cloud</artifactId>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

## 子项目列表

|子项目|说明|
|:----|:----|
|guerlab-cloud-api|feign的扩展支持|
|guerlab-cloud-api-core|feign的核心扩展支持，提供debug环境下自动重定向、数据解析优化扩展|
|guerlab-cloud-api-rest|对rest端点的feign扩展支持|
|guerlab-cloud-auth|授权模块|
|guerlab-cloud-auth-commons|提供授权的公共配置，内置jwk\md5\rc4格式的数据加解密支持|
|guerlab-cloud-auth-redis-token-support|提供基于redis的token令牌授权模型支持|
|guerlab-cloud-auth-web-support|提供授权模块在web环境下的通用支持|
|guerlab-cloud-auth-webflux-support|提供授权模块在webflux环境下的使用支持|
|guerlab-cloud-auth-webmvc-support|提供授权模块在webmvc环境下的使用支持|
|guerlab-cloud-cache|提供缓存的统一处理|
|guerlab-cloud-cache-redis|提供redis缓存的优化扩展|
|guerlab-cloud-commons|基础包，提供部分domain与工具类|
|guerlab-cloud-context|请求上下文增强|
|guerlab-cloud-context-core|请求上下文增强的核心实现|
|guerlab-cloud-context-webflux|请求上下文在webflux增强的核心实现|
|guerlab-cloud-context-webmvc|请求上下文在webmvc增强的核心实现|
|guerlab-cloud-core|框架核心|
|guerlab-cloud-distributed|基于redis的分布式锁支持|
|guerlab-cloud-excel|提供excel的的简易使用|
|guerlab-cloud-geo|提供对地理位置信息的数据结构支持|
|guerlab-cloud-idempotent|提供基于redis的幂等锁支持|
|guerlab-cloud-loadbalancer|提供对服务发现的负载均衡增强，包含版本控制与集群控制|
|guerlab-cloud-log|提供对日志切入点的统一处理|
|guerlab-cloud-openapi|openapi文档支持|
|guerlab-cloud-openapi-core|openapi文档的核心支持|
|guerlab-cloud-openapi-webflux|openapi文档在webflux环境下的扩展支持|
|guerlab-cloud-openapi-webmvc|openapi文档在webmvc环境下的扩展支持|
|guerlab-cloud-redis|提供对redis的基础支持扩展|
|guerlab-cloud-rsa|提供对rsa的支持，包含证书生成、加解密|
|guerlab-cloud-searchparams|提供搜索参数对象及相关的工具类|
|guerlab-cloud-searchparams-core|提供搜索参数对象定义及核心工具类|
|guerlab-cloud-searchparams-mybatisplus|提供搜索参数对象在mybatis-plus环境下的使用支持|
|guerlab-cloud-security|对spring security的增强支持|
|guerlab-cloud-security-core|对spring security的增强支持核心实现|
|guerlab-cloud-security-webflux|对spring security在webflux环境下的增强支持|
|guerlab-cloud-security-webmvc|对spring security在webmvc环境下的增强支持|
|guerlab-cloud-sentinel|提供sentinel的扩展支持|
|guerlab-cloud-sentinel-webflux|对sentinel在webflux环境下的增强支持|
|guerlab-cloud-sentinel-webmvc|对sentinel在webmvc环境下的增强支持|
|guerlab-cloud-server|服务实现|
|guerlab-cloud-server-api-rest|服务端的rest端点支持|
|guerlab-cloud-server-commons|server实现的基础支持|
|guerlab-cloud-server-orm|提供orm的扩展支持|
|guerlab-cloud-server-rest|提供通用的rest端点的定义|
|guerlab-cloud-stream|stream数据处理工具包|
|guerlab-cloud-web|提供web环境的增强|
|guerlab-cloud-web-core|提供web环境的通用增强|
|guerlab-cloud-web-webflux|额外提供webflux环境下的增强|
|guerlab-cloud-web-webmvc|额外提供webmvc环境下的增强|

## wiki

- [Gitee](https://gitee.com/guerlab_net/guerlab-cloud/wikis/pages)

## changelog

- [Gitee](https://gitee.com/guerlab_net/guerlab-cloud/wikis/pages)
