/*
 * Copyright (c) 2018, vindell (https://github.com/vindell).
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

/**
 * TODO
 * @author 		： <a href="https://github.com/vindell">vindell</a>
 */
@ConfigurationProperties(Swagger2GatewayProperties.PREFIX)
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

	public boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isEnableUrlTemplating() {
		return enableUrlTemplating;
	}

	public void setEnableUrlTemplating(boolean enableUrlTemplating) {
		this.enableUrlTemplating = enableUrlTemplating;
	}

	public boolean isForCodeGen() {
		return forCodeGen;
	}

	public void setForCodeGen(boolean forCodeGen) {
		this.forCodeGen = forCodeGen;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public String getLicenseUrl() {
		return licenseUrl;
	}

	public void setLicenseUrl(String licenseUrl) {
		this.licenseUrl = licenseUrl;
	}

	public String getTermsOfServiceUrl() {
		return termsOfServiceUrl;
	}

	public void setTermsOfServiceUrl(String termsOfServiceUrl) {
		this.termsOfServiceUrl = termsOfServiceUrl;
	}

	public List<Class<?>> getIgnoredParameterTypes() {
		return ignoredParameterTypes;
	}

	public void setIgnoredParameterTypes(List<Class<?>> ignoredParameterTypes) {
		this.ignoredParameterTypes = ignoredParameterTypes;
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public String getBasePackage() {
		return basePackage;
	}

	public void setBasePackage(String basePackage) {
		this.basePackage = basePackage;
	}

	public String getBasePathPattern() {
		return basePathPattern;
	}

	public void setBasePathPattern(String basePathPattern) {
		this.basePathPattern = basePathPattern;
	}

	public Map<String, DocketInfo> getDocket() {
		return docket;
	}

	public void setDocket(Map<String, DocketInfo> docket) {
		this.docket = docket;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public List<GlobalOperationParameter> getGlobalOperationParameters() {
		return globalOperationParameters;
	}

	public void setGlobalOperationParameters(List<GlobalOperationParameter> globalOperationParameters) {
		this.globalOperationParameters = globalOperationParameters;
	}

	public UiConfig getUiConfig() {
		return uiConfig;
	}

	public void setUiConfig(UiConfig uiConfig) {
		this.uiConfig = uiConfig;
	}

	public boolean getApplyDefaultResponseMessages() {
		return applyDefaultResponseMessages;
	}

	public void setApplyDefaultResponseMessages(boolean applyDefaultResponseMessages) {
		this.applyDefaultResponseMessages = applyDefaultResponseMessages;
	}

	public GlobalResponseMessage getGlobalResponseMessage() {
		return globalResponseMessage;
	}

	public void setGlobalResponseMessage(GlobalResponseMessage globalResponseMessage) {
		this.globalResponseMessage = globalResponseMessage;
	}

	public Authorization getAuthorization() {
		return authorization;
	}

	public void setAuthorization(Authorization authorization) {
		this.authorization = authorization;
	}
	
}
