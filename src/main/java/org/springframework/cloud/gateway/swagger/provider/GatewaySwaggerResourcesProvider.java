package org.springframework.cloud.gateway.swagger.provider;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.support.NameUtils;
import org.springframework.cloud.gateway.swagger.Swagger2GatewayProperties;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;

import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

public class GatewaySwaggerResourcesProvider implements SwaggerResourcesProvider {

	public static final AntPathMatcher matcher  = new AntPathMatcher();
	public static final String API_URI = "/v2/api-docs";
	public static final String API_EXT_URI = "/v2/api-docs-ext";
	private final RouteLocator routeLocator;
	private final GatewayProperties gatewayProperties;
	private final Swagger2GatewayProperties swagger2GatewayProperties;

	public GatewaySwaggerResourcesProvider(RouteLocator routeLocator, 
			GatewayProperties gatewayProperties,
			Swagger2GatewayProperties swagger2GatewayProperties) {
		this.routeLocator = routeLocator;
		this.gatewayProperties = gatewayProperties;
		this.swagger2GatewayProperties = swagger2GatewayProperties;
	}

	@Override
	public List<SwaggerResource> get() {
		List<SwaggerResource> resources = new ArrayList<>();
		// routes
		List<String> routes = new ArrayList<>();
		if(StringUtils.hasText(swagger2GatewayProperties.getRoutePattern())) {
			routeLocator.getRoutes().filter(route -> matcher.match(swagger2GatewayProperties.getRoutePattern(), route.getId())).subscribe(route -> {
				routes.add(route.getId());
			});
		} else {
			routeLocator.getRoutes().subscribe(route -> {
				routes.add(route.getId());
			});
		}
		// routes
		gatewayProperties.getRoutes().stream().filter(routeDefinition -> routes.contains(routeDefinition.getId()))
				.forEach(routeDefinition -> routeDefinition.getPredicates().stream()
						.filter(predicateDefinition -> ("Path").equalsIgnoreCase(predicateDefinition.getName()))
						.forEach(predicateDefinition -> resources
								.add(swaggerResource(routeDefinition.getId(), predicateDefinition.getArgs()
										.get(NameUtils.GENERATED_NAME_PREFIX + "0").replace("/**", API_URI)))));
		return resources;
	}

	private SwaggerResource swaggerResource(String name, String location) {
		SwaggerResource swaggerResource = new SwaggerResource();
		swaggerResource.setName(name);
		swaggerResource.setLocation(location);
		swaggerResource.setSwaggerVersion("2.0");
		return swaggerResource;
	}
}
