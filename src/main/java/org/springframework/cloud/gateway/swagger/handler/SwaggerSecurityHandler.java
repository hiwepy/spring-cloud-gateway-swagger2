package org.springframework.cloud.gateway.swagger.handler;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;

public class SwaggerSecurityHandler implements HandlerFunction<ServerResponse> {

    private SecurityConfiguration securityConfiguration;
    
    public SwaggerSecurityHandler(SecurityConfiguration securityConfiguration) {
		this.securityConfiguration = securityConfiguration;
	}

    @Override
    public Mono<ServerResponse> handle(ServerRequest request) {
        return ServerResponse.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(
                        Optional.ofNullable(securityConfiguration)
								.orElse(SecurityConfigurationBuilder.builder().build())));
	}

}