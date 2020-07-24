package org.springframework.cloud.gateway.swagger;

import org.springframework.util.StringUtils;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

/**
 * http://springfox.github.io/springfox/docs/current/#history
 */
public class Swagger2UiWebFluxConfigurer implements WebFluxConfigurer {
	
	private final String baseUrl;

	public Swagger2UiWebFluxConfigurer(String baseUrl) {
	    this.baseUrl = baseUrl;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		String baseUrl = StringUtils.trimTrailingCharacter(this.baseUrl, '/');
		registry.addResourceHandler(baseUrl + "/swagger-ui/**")
				.addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/")
				.resourceChain(false);
	}
	
}
