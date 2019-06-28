
# spring-cloud-gateway-swagger2 简介

spring-cloud-gateway-swagger2 是一个spring boot starter 插件，它实现了分布在每个独立微服务文档数据的汇集和展示。

只需要引入依赖，简单配置，即可在网关服务统一查看文档信息。

# 集成与使用

### Maven配置

```xml
<dependency>
	<groupId>com.github.vindell</groupId>
	<artifactId>spring-cloud-gateway-swagger2</artifactId>
	<version>${project.version}</version>
</dependency>
```

### 网关配置

```yaml
################################################################################################################
###Spring Boot 相关组件（SpringMVC、Freemarker、Session、Cache、DataSource）配置：
################################################################################################################
spring:
  cloud:
    gateway:
      enabled: true
      discovery:
        locator:
          enabled: true
      filter-map:
        '[/]': anon
        '[/**/favicon.ico]': anon
        '[/webjars/**]': anon
        '[/assets/**]': anon
        '[/error*]': anon
        '[/logo/**]': anon
        '[/swagger-ui.html**]': anon
        '[/swagger-resources/**]': anon
        '[/doc.html]': anon
        '[/**/bycdao-ui/**]': anon
        '[/**/v2/**]': anon
        '[/kaptcha*]': anon
        '[/actuator*]': anon
        '[/actuator/**]': anon
        '[/dingtalk/**]': anon
        '[/**/authz/login/jwt]': anon
        '[/**/authz/login/dingtalk]': anon
      swagger:
        enabled: true
        authorization:
          key-name: X-Authorization
        title: XXX平台 - 服务网关
        description: 该模块完成各模块的接口中转
        version: ${application.version}
        contact:
          name: hiwepy
          url: http://hiwepy.com
        base-package: com.hiwepy
        # 公共参数
        global-operation-parameters:
          - name: X-Authorization
            description: JWT鉴权
            modelRef: string
            parameterType: header
            required: true
      metrics:
        enabled: true
      globalcors:
        corsConfigurations:
          '[/**]':
            allowedOrigins: "*"
            allowedMethods:
            - GET
            - POST
      default-filters:
        #- name: RateLimiter
        - name: Hystrix
          args:
            name: fallbackcmd
            fallbackUri: forward:/fallback
        - name: Retry
          args:
            retries: 3
            statuses: BAD_GATEWAY
      routes:
        - id: hiwepy-authz
          uri: lb://hiwepy-authz
          predicates:
            - Path=/hiwepy-authz/**
          filters:
            - StripPrefix=1
            #- name: RequestRateLimiter
              #args: 
                #key-resolver: '#{@hostAddrKeyResolver}' 
                #redis-rate-limiter.replenishRate: 10
                #redis-rate-limiter.burstCapacity: 30
        - id: hiwepy-inform
          uri: lb://hiwepy-inform
          predicates:
            - Path=/hiwepy-inform/**
          filters:
            - StripPrefix=1
        - id: hiwepy-settings
          uri: lb://hiwepy-settings
          predicates:
            - Path=/hiwepy-settings/**
          filters:
            - StripPrefix=1
        - id: hiwepy-baseinfo
          uri: lb://hiwepy-baseinfo
          predicates:
            - Path=/hiwepy-baseinfo/**
          filters:
            - StripPrefix=1
```

# 项目截图


# 依赖组件

- swagger-bootstrap-ui 1.9.4
- swagger 1.5.22
- springfox 3.0.0-SNAPSHOT