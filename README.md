# Guerlab Cloud

![](https://img.shields.io/maven-central/v/net.guerlab.cloud/guerlab-cloud.svg)
![](https://img.shields.io/badge/LICENSE-LGPL--3.0-brightgreen.svg)

采用java14进行开发<br>
基于Spring Cloud与Spring Cloud Alibaba的扩展框架，提供feign扩展、授权扩展、缓存扩展、国际化扩展、excel扩展、server扩展与stream扩展<br>

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
|guerlab-cloud-api|feign的扩展支持，提供debug环境下自动重定向、数据解析优化扩展|
|guerlab-cloud-auth|授权模块|
|guerlab-cloud-auth-commons|提供授权的公共配置，内置jwk\md5\rc4格式的数据加解密支持|
|guerlab-cloud-auth-redis-token-support|提供基于redis的token令牌授权模型支持|
|guerlab-cloud-auth-web-support|提供授权模块在web环境下的使用支持|
|guerlab-cloud-cache|提供缓存的统一处理|
|guerlab-cloud-cache-redis|提供redis缓存的优化扩展|
|guerlab-cloud-commons|基础包，提供部分domain与工具类|
|guerlab-cloud-excel|提供excel的的简易使用|
|guerlab-cloud-i18n|提供基于MessageSource的多模块国际化支持|
|guerlab-cloud-loadbalancer|提供对服务发现的负载均衡增强，包含版本控制与集群控制|
|guerlab-cloud-log|提供对日志切入点的统一处理|
|guerlab-cloud-server|服务实现|
|guerlab-cloud-server-commons|server实现的基础支持|
|guerlab-cloud-server-openapi-commons|openapi文档的基础支持|
|guerlab-cloud-server-openapi-webflux|openapi文档在webflux环境下的扩展支持|
|guerlab-cloud-server-openapi-webmvc|openapi文档在webmvc环境下的扩展支持|
|guerlab-cloud-server-orm|提供orm的扩展支持|
|guerlab-cloud-server-sentinel|提供sentinel的扩展支持|
|guerlab-cloud-server-webflux|在webflux环境下的扩展支持|
|guerlab-cloud-server-webmvc|在webmvc环境下的扩展支持|
|guerlab-cloud-server-stream|stream数据处理工具包|

## wiki

- [Gitee](https://gitee.com/guerlab_net/guerlab-cloud/wikis/pages)

## changelog

- [Gitee](https://gitee.com/guerlab_net/guerlab-cloud/wikis/pages)
