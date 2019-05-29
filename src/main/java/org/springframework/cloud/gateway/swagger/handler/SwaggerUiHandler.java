package org.springframework.cloud.gateway.swagger.handler;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;

public class SwaggerUiHandler implements HandlerFunction<ServerResponse> {

	private UiConfiguration uiConfiguration;

	public SwaggerUiHandler(UiConfiguration uiConfiguration) {
		this.uiConfiguration = uiConfiguration;
	}

	@Override
	public Mono<ServerResponse> handle(ServerRequest request) {
		return ServerResponse.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON_UTF8).body(BodyInserters
				.fromObject(Optional.ofNullable(uiConfiguration).orElse(UiConfigurationBuilder.builder().build())));
	}

}