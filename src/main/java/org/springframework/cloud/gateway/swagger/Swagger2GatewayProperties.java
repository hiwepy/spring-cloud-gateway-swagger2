/*
 * Copyright (c) 2018, hiwepy (https://github.com/hiwepy).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.springframework.cloud.gateway.swagger;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.gateway.swagger.model.Authorization;
import org.springframework.cloud.gateway.swagger.model.Contact;
import org.springframework.cloud.gateway.swagger.model.DocketInfo;
import org.springframework.cloud.gateway.swagger.model.GlobalOperationParameter;
import org.springframework.cloud.gateway.swagger.model.GlobalResponseMessage;
import org.springframework.cloud.gateway.swagger.model.UiConfig;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * TODO
 * @author 		： <a href="https://github.com/hiwepy">hiwepy</a>
 */
@ConfigurationProperties(Swagger2GatewayProperties.PREFIX)
@Getter
@Setter
@ToString
public class Swagger2GatewayProperties {

	public static final String PREFIX = "spring.cloud.gateway.swagger";
	
	/**
	 * 是否开启swagger
	 **/
	private boolean enabled;
	/**
	 * when true it enables rfc6570 url templates
	 */
	private boolean enableUrlTemplating = false;
	/**
	 * Set this to true in order to make the documentation code generation friendly.
	 *
	 * true|false determines the naming strategy used
	 */
	private boolean forCodeGen = false;
	/**
	 * 标题
	 **/
	private String title = "";
	/**
	 * 描述
	 **/
	private String description = "";
	/**
	 * 版本
	 **/
	private String version = "";
	/**
	 * 许可证
	 **/
	private String license = "";
	/**
	 * 许可证URL
	 **/
	private String licenseUrl = "";
	/**
	 * 服务条款URL
	 **/
	private String termsOfServiceUrl = "";

	/**
	 * 忽略的参数类型
	 **/
	private List<Class<?>> ignoredParameterTypes = new ArrayList<>();

	private Contact contact = new Contact();

	/**
	 * swagger会解析的包路径
	 **/
	private String basePackage = "";

	/**
     * swagger会解析的url规则:ant表达式
     **/
    private String basePathPattern = "";
    
	/**
     * swagger API 路由ID表达式，不指定则使用所有的路由
     */
	private String routePattern = "";

	/**
	 * 分组文档
	 **/
	private Map<String, DocketInfo> docket = new LinkedHashMap<>();

	/**
	 * host信息
	 **/
	private String host = "";
	  
	/**
	 * 全局参数配置
	 **/
	private List<GlobalOperationParameter> globalOperationParameters;

	/**
	 * 页面功能配置
	 **/
	private UiConfig uiConfig = new UiConfig();

	/**
	 * 是否使用默认预定义的响应消息 ，默认 true
	 **/
	private boolean applyDefaultResponseMessages = true;

	/**
	 * 全局响应消息
	 **/
	private GlobalResponseMessage globalResponseMessage;

	/**
	 * 全局统一鉴权配置
	 **/
	private Authorization authorization = new Authorization();

	
}
