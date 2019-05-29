package org.springframework.cloud.gateway.swagger.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.swagger.provider.GatewaySwaggerResourcesProvider;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;

@Component
public class SwaggerHeaderFilter extends AbstractGatewayFilterFactory<Object> {

	private static final String HEADER_NAME = "X-Forwarded-Prefix";

	@Override
	public GatewayFilter apply(Object config) {
		return (exchange, chain) -> {
			ServerHttpRequest request = exchange.getRequest();
			String path = request.getURI().getPath();
			if (!StringUtils.endsWithIgnoreCase(path, GatewaySwaggerResourcesProvider.API_URI)) {
				return chain.filter(exchange);
			}
			String basePath = path.substring(0, path.lastIndexOf(GatewaySwaggerResourcesProvider.API_URI));
			ServerHttpRequest newRequest = request.mutate().header(HEADER_NAME, basePath).build();
			ServerWebExchange newExchange = exchange.mutate().request(newRequest).build();
			return chain.filter(newExchange);
		};
	}
	
}