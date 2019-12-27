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
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.swagger.filter.SwaggerHeaderFilter;
import org.springframework.cloud.gateway.swagger.handler.SwaggerResourceHandler;
import org.springframework.cloud.gateway.swagger.handler.SwaggerSecurityHandler;
import org.springframework.cloud.gateway.swagger.handler.SwaggerUiHandler;
import org.springframework.cloud.gateway.swagger.model.DocketInfo;
import org.springframework.cloud.gateway.swagger.model.GlobalOperationParameter;
import org.springframework.cloud.gateway.swagger.model.GlobalResponseMessage;
import org.springframework.cloud.gateway.swagger.model.GlobalResponseMessageBody;
import org.springframework.cloud.gateway.swagger.provider.GatewaySwaggerResourcesProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.BasicAuth;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebFlux;

/**
 *	 参考：
 * http://www.spring4all.com/article/1381
 * https://my.oschina.net/go4it/blog/3035218
 * 	部分代码来来自：https://github.com/SpringForAll/spring-boot-starter-swagger
 */
@Configuration
@ConditionalOnProperty(prefix = Swagger2GatewayProperties.PREFIX, value = "enabled", havingValue = "true")
@EnableConfigurationProperties({ Swagger2GatewayProperties.class })
@EnableSwagger2WebFlux
@Import({ BeanValidatorPluginsConfiguration.class })
public class Swagger2GatewayAutoConfiguration implements BeanFactoryAware, WebFluxConfigurer {

	private BeanFactory beanFactory;

	@Bean
	public UiConfiguration uiConfiguration(Swagger2GatewayProperties swaggerProperties) {
		return UiConfigurationBuilder.builder()
				.deepLinking(swaggerProperties.getUiConfig().getDeepLinking())
				.defaultModelExpandDepth(swaggerProperties.getUiConfig().getDefaultModelExpandDepth())
				.defaultModelRendering(swaggerProperties.getUiConfig().getDefaultModelRendering())
				.defaultModelsExpandDepth(swaggerProperties.getUiConfig().getDefaultModelsExpandDepth())
				.displayOperationId(swaggerProperties.getUiConfig().getDisplayOperationId())
				.displayRequestDuration(swaggerProperties.getUiConfig().getDisplayRequestDuration())
				.docExpansion(swaggerProperties.getUiConfig().getDocExpansion())
				.maxDisplayedTags(swaggerProperties.getUiConfig().getMaxDisplayedTags())
				.operationsSorter(swaggerProperties.getUiConfig().getOperationsSorter())
				.showExtensions(swaggerProperties.getUiConfig().getShowExtensions())
				.tagsSorter(swaggerProperties.getUiConfig().getTagsSorter())
				.validatorUrl(swaggerProperties.getUiConfig().getValidatorUrl())
				.build();
	}

	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnBean(UiConfiguration.class)
	@ConditionalOnProperty(prefix = Swagger2GatewayProperties.PREFIX, name = "enabled", matchIfMissing = true)
	public List<Docket> createRestApi(Swagger2GatewayProperties swaggerProperties) {
		ConfigurableBeanFactory configurableBeanFactory = (ConfigurableBeanFactory) beanFactory;
		List<Docket> docketList = new LinkedList<>();

		// 没有分组
		if (CollectionUtils.isEmpty(swaggerProperties.getDocket())) {
			
			ApiInfo apiInfo = new ApiInfoBuilder()
					.title(swaggerProperties.getTitle())
					.description(swaggerProperties.getDescription())
					.version(swaggerProperties.getVersion())
					.license(swaggerProperties.getLicense())
					.licenseUrl(swaggerProperties.getLicenseUrl())
					.contact(new Contact(swaggerProperties.getContact().getName(), swaggerProperties.getContact().getUrl(), swaggerProperties.getContact().getEmail()))
					.termsOfServiceUrl(swaggerProperties.getTermsOfServiceUrl())
					.build();

			Docket docketForBuilder = new Docket(DocumentationType.SWAGGER_2)
					.host(swaggerProperties.getHost())
					.apiInfo(apiInfo)
					.securityContexts(Collections.singletonList(securityContext(swaggerProperties)))
					.globalOperationParameters(buildGlobalOperationParametersFromSwagger2WebFluxProperties( swaggerProperties.getGlobalOperationParameters()));

			switch (swaggerProperties.getAuthorization().getType()) {
				case APIKEY:{
					docketForBuilder.securitySchemes(Collections.singletonList(apiKey(swaggerProperties)));
				};break;
				case BASICAUTH:{
					docketForBuilder.securitySchemes(Collections.singletonList(basicAuth(swaggerProperties)));
				};break;
				default:{
					
				};break;
			}

			// 全局响应消息
			if (!swaggerProperties.getApplyDefaultResponseMessages()) {
				buildGlobalResponseMessage(swaggerProperties, docketForBuilder);
			}

			Docket docket = docketForBuilder.select()
					.apis(RequestHandlerSelectors.basePackage(swaggerProperties.getBasePackage()))
					.paths(StringUtils.hasText(swaggerProperties.getBasePathPattern()) ? PathSelectors.ant(swaggerProperties.getBasePathPattern()) : PathSelectors.any())
					.build();

			/* ignoredParameterTypes **/
			Class<?>[] array = new Class[swaggerProperties.getIgnoredParameterTypes().size()];
			Class<?>[] ignoredParameterTypes = swaggerProperties.getIgnoredParameterTypes().toArray(array);
			docket.ignoredParameterTypes(ignoredParameterTypes)
					.enableUrlTemplating(swaggerProperties.isEnableUrlTemplating())
					.forCodeGeneration(swaggerProperties.isForCodeGen());

			configurableBeanFactory.registerSingleton("defaultDocket", docket);
			
			docketList.add(docket);
			
			return docketList;
			
		} 
		
		// 分组创建
		for (String groupName : swaggerProperties.getDocket().keySet()) {
			
			DocketInfo docketInfo = swaggerProperties.getDocket().get(groupName);

			ApiInfo apiInfo = new ApiInfoBuilder()
					.title(docketInfo.getTitle().isEmpty() ? swaggerProperties.getTitle() : docketInfo.getTitle())
					.description(docketInfo.getDescription().isEmpty() ? swaggerProperties.getDescription() : docketInfo.getDescription())
					.version(docketInfo.getVersion().isEmpty() ? swaggerProperties.getVersion() : docketInfo.getVersion())
					.license(docketInfo.getLicense().isEmpty() ? swaggerProperties.getLicense() : docketInfo.getLicense())
					.licenseUrl(docketInfo.getLicenseUrl().isEmpty() ? swaggerProperties.getLicenseUrl() : docketInfo.getLicenseUrl())
					.contact(new Contact(
							docketInfo.getContact().getName().isEmpty() ? swaggerProperties.getContact().getName() : docketInfo.getContact().getName(),
							docketInfo.getContact().getUrl().isEmpty() ? swaggerProperties.getContact().getUrl() : docketInfo.getContact().getUrl(),
							docketInfo.getContact().getEmail().isEmpty() ? swaggerProperties.getContact().getEmail() : docketInfo.getContact().getEmail()))
					.termsOfServiceUrl(
							docketInfo.getTermsOfServiceUrl().isEmpty() ? swaggerProperties.getTermsOfServiceUrl() : docketInfo.getTermsOfServiceUrl())
					.build();

			Docket docketForBuilder = new Docket(DocumentationType.SWAGGER_2).host(swaggerProperties.getHost())
					.apiInfo(apiInfo)
					.securityContexts(Collections.singletonList(securityContext(swaggerProperties)))
					.globalOperationParameters(assemblyGlobalOperationParameters(swaggerProperties.getGlobalOperationParameters(), docketInfo.getGlobalOperationParameters()));

			switch (swaggerProperties.getAuthorization().getType()) {
				case APIKEY:{
					docketForBuilder.securitySchemes(Collections.singletonList(apiKey(swaggerProperties)));
				};break;
				case BASICAUTH:{
					docketForBuilder.securitySchemes(Collections.singletonList(basicAuth(swaggerProperties)));
				};break;
				default:{
					
				};break;
			}

			// 全局响应消息
			if (!swaggerProperties.getApplyDefaultResponseMessages()) {
				buildGlobalResponseMessage(swaggerProperties, docketForBuilder);
			}

			Docket docket = docketForBuilder.groupName(groupName).select()
					.apis(RequestHandlerSelectors.basePackage(docketInfo.getBasePackage()))
					.paths(StringUtils.hasText(docketInfo.getBasePathPattern()) ? PathSelectors.ant(docketInfo.getBasePathPattern()) : PathSelectors.any())
					.build();

			/* ignoredParameterTypes **/
			Class<?>[] array = new Class[docketInfo.getIgnoredParameterTypes().size()];
			Class<?>[] ignoredParameterTypes = docketInfo.getIgnoredParameterTypes().toArray(array);
			docket.ignoredParameterTypes(ignoredParameterTypes)
					.enableUrlTemplating(docketInfo.isEnableUrlTemplating())
					.forCodeGeneration(docketInfo.isForCodeGen());

			configurableBeanFactory.registerSingleton(groupName, docket);
			
			docketList.add(docket);
		}
			
		return docketList;
	}

	/**
	 * 配置基于 ApiKey 的鉴权对象
	 *
	 * @return
	 */
	private ApiKey apiKey(Swagger2GatewayProperties swaggerProperties) {
		return new ApiKey(swaggerProperties.getAuthorization().getName(),
				swaggerProperties.getAuthorization().getKeyName(), ApiKeyVehicle.HEADER.getValue());
	}

	/**
	 * 配置基于 BasicAuth 的鉴权对象
	 *
	 * @return
	 */
	private BasicAuth basicAuth(Swagger2GatewayProperties swaggerProperties) {
		return new BasicAuth(swaggerProperties.getAuthorization().getName());
	}

	/**
	 * 配置默认的全局鉴权策略的开关，以及通过正则表达式进行匹配；默认 ^.*$ 匹配所有URL 其中 securityReferences 为配置启用的鉴权策略
	 *
	 * @return
	 */
	private SecurityContext securityContext(Swagger2GatewayProperties swaggerProperties) {
		return SecurityContext.builder().securityReferences(defaultAuth(swaggerProperties))
				.forPaths(PathSelectors.regex(swaggerProperties.getAuthorization().getAuthRegex())).build();
	}

	/**
	 * 配置默认的全局鉴权策略；其中返回的 SecurityReference 中，reference
	 * 即为ApiKey对象里面的name，保持一致才能开启全局鉴权
	 *
	 * @return
	 */
	private List<SecurityReference> defaultAuth(Swagger2GatewayProperties swaggerProperties) {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return Collections.singletonList(SecurityReference.builder()
				.reference(swaggerProperties.getAuthorization().getName()).scopes(authorizationScopes).build());
	}

	private List<Parameter> buildGlobalOperationParametersFromSwagger2WebFluxProperties(
			List<GlobalOperationParameter> globalOperationParameters) {
		List<Parameter> parameters = new ArrayList<Parameter>();

		if (Objects.isNull(globalOperationParameters)) {
			return parameters;
		}
		for (GlobalOperationParameter globalOperationParameter : globalOperationParameters) {
			parameters.add(new ParameterBuilder().name(globalOperationParameter.getName())
					.description(globalOperationParameter.getDescription())
					.modelRef(new ModelRef(globalOperationParameter.getModelRef()))
					.parameterType(globalOperationParameter.getParameterType())
					.required(Boolean.parseBoolean(globalOperationParameter.getRequired())).build());
		}
		return parameters;
	}

	/**
	 * 局部参数按照name覆盖局部参数
	 *
	 * @param globalOperationParameters
	 * @param docketOperationParameters
	 * @return
	 */
	private List<Parameter> assemblyGlobalOperationParameters(List<GlobalOperationParameter> globalOperationParameters,
			List<GlobalOperationParameter> docketOperationParameters) {

		if (Objects.isNull(docketOperationParameters) || docketOperationParameters.isEmpty()) {
			return buildGlobalOperationParametersFromSwagger2WebFluxProperties(globalOperationParameters);
		}

		Set<String> docketNames = docketOperationParameters.stream().map(GlobalOperationParameter::getName)
				.collect(Collectors.toSet());

		List<GlobalOperationParameter> resultOperationParameters = new ArrayList<GlobalOperationParameter>();

		if (Objects.nonNull(globalOperationParameters)) {
			for (GlobalOperationParameter parameter : globalOperationParameters) {
				if (!docketNames.contains(parameter.getName())) {
					resultOperationParameters.add(parameter);
				}
			}
		}

		resultOperationParameters.addAll(docketOperationParameters);
		return buildGlobalOperationParametersFromSwagger2WebFluxProperties(resultOperationParameters);
	}

	/**
	 * 设置全局响应消息
	 *
	 * @param swaggerProperties swaggerProperties 支持 POST,GET,PUT,PATCH,DELETE,HEAD,OPTIONS,TRACE
	 * @param docketForBuilder  swagger docket builder
	 */
	private void buildGlobalResponseMessage(Swagger2GatewayProperties swaggerProperties, Docket docketForBuilder) {

		GlobalResponseMessage globalResponseMessages = swaggerProperties.getGlobalResponseMessage();

		/* POST,GET,PUT,PATCH,DELETE,HEAD,OPTIONS,TRACE 响应消息体 **/
		List<ResponseMessage> postResponseMessages = getResponseMessageList(globalResponseMessages.getPost());
		List<ResponseMessage> getResponseMessages = getResponseMessageList(globalResponseMessages.getGet());
		List<ResponseMessage> putResponseMessages = getResponseMessageList(globalResponseMessages.getPut());
		List<ResponseMessage> patchResponseMessages = getResponseMessageList(globalResponseMessages.getPatch());
		List<ResponseMessage> deleteResponseMessages = getResponseMessageList(globalResponseMessages.getDelete());
		List<ResponseMessage> headResponseMessages = getResponseMessageList(globalResponseMessages.getHead());
		List<ResponseMessage> optionsResponseMessages = getResponseMessageList(globalResponseMessages.getOptions());
		List<ResponseMessage> trackResponseMessages = getResponseMessageList(globalResponseMessages.getTrace());

		docketForBuilder.useDefaultResponseMessages(swaggerProperties.getApplyDefaultResponseMessages())
				.globalResponseMessage(RequestMethod.POST, postResponseMessages)
				.globalResponseMessage(RequestMethod.GET, getResponseMessages)
				.globalResponseMessage(RequestMethod.PUT, putResponseMessages)
				.globalResponseMessage(RequestMethod.PATCH, patchResponseMessages)
				.globalResponseMessage(RequestMethod.DELETE, deleteResponseMessages)
				.globalResponseMessage(RequestMethod.HEAD, headResponseMessages)
				.globalResponseMessage(RequestMethod.OPTIONS, optionsResponseMessages)
				.globalResponseMessage(RequestMethod.TRACE, trackResponseMessages);
	}

	/**
	 * 获取返回消息体列表
	 * @param globalResponseMessageBodyList 全局Code消息返回集合
	 * @return
	 */
	private List<ResponseMessage> getResponseMessageList(
			List<GlobalResponseMessageBody> globalResponseMessageBodyList) {
		List<ResponseMessage> responseMessages = new ArrayList<>();
		for (GlobalResponseMessageBody globalResponseMessageBody : globalResponseMessageBodyList) {
			ResponseMessageBuilder responseMessageBuilder = new ResponseMessageBuilder();
			responseMessageBuilder.code(globalResponseMessageBody.getCode()).message(globalResponseMessageBody.getMessage());

			if (!StringUtils.isEmpty(globalResponseMessageBody.getModelRef())) {
				responseMessageBuilder.responseModel(new ModelRef(globalResponseMessageBody.getModelRef()));
			}
			responseMessages.add(responseMessageBuilder.build());
		}

		return responseMessages;
	}
	
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		if(!registry.hasMappingForPattern("/doc.html**")) {
			registry.addResourceHandler("/doc.html**").addResourceLocations("classpath:/META-INF/resources/");
		}
		if(!registry.hasMappingForPattern("/swagger-ui.html**")) {
			registry.addResourceHandler("/swagger-ui.html**").addResourceLocations("classpath:/META-INF/resources/");
		}
		if(!registry.hasMappingForPattern("/webjars/**")) {
			registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
			//registry.addResourceHandler("/webjars/**").addResourceLocations("/webjars/").resourceChain(true).addResolver(new WebJarsResourceResolver());
		}
	}
	
	@Bean
	@Primary
	public SwaggerResourcesProvider swaggerResources(RouteLocator routeLocator, GatewayProperties gatewayProperties) {
		return new GatewaySwaggerResourcesProvider(routeLocator, gatewayProperties);
	}

	@Bean
	public SwaggerResourceHandler swaggerResourceHandler(SwaggerResourcesProvider swaggerResources) {
		return new SwaggerResourceHandler(swaggerResources);
	}

	@Bean
	public SwaggerSecurityHandler swaggerSecurityHandler(
			@Autowired(required = false) SecurityConfiguration securityConfiguration) {
		return new SwaggerSecurityHandler(securityConfiguration);
	}

	@Bean
	public SwaggerUiHandler swaggerUiHandler(@Autowired(required = false) UiConfiguration uiConfiguration) {
		return new SwaggerUiHandler(uiConfiguration);
	}

	@Bean
	public RouterFunction<?> routerFunction(SwaggerResourceHandler swaggerResourceHandler,
			SwaggerSecurityHandler swaggerSecurityHandler, SwaggerUiHandler swaggerUiHandler) {
		return RouterFunctions
				.route(RequestPredicates.GET("/swagger-resources").and(RequestPredicates.accept(MediaType.ALL)),
						swaggerResourceHandler)
				.andRoute(RequestPredicates.GET("/swagger-resources/configuration/ui")
						.and(RequestPredicates.accept(MediaType.ALL)), swaggerUiHandler)
				.andRoute(RequestPredicates.GET("/swagger-resources/configuration/security")
						.and(RequestPredicates.accept(MediaType.ALL)), swaggerSecurityHandler);
	}
	
	@Bean
	public SwaggerHeaderFilter swaggerHeaderFilter() {
		return new SwaggerHeaderFilter();
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

}