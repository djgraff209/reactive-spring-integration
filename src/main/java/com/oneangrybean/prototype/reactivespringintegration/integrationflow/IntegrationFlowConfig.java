package com.oneangrybean.prototype.reactivespringintegration.integrationflow;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.Transformers;
import org.springframework.integration.webflux.dsl.WebFlux;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Configuration
public class IntegrationFlowConfig {
    @Bean
    public WebClient webClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder.build();
    }

    @MessagingGateway
    public interface MyObjectInformationGateway {
        @Gateway(requestChannel = "readMyObject.input")
        @Payload("new java.util.Date()")
        public Mono<List<String>> readMyObjects();
    }

    @Bean
    public IntegrationFlow readMyObject(final WebClient webClient) {
        return f -> f
            .handle(
                WebFlux.outboundGateway("http://localhost:8080/myobject", webClient)
                        .httpMethod(HttpMethod.GET)
                        .mappedResponseHeaders()
                        .expectedResponseType(String.class)
            )
            .transform(Transformers.fromJson())
            .transform("#jsonPath(payload, '$.value')")
            .split()
            .enrichHeaders(e -> e.headerExpression("myObjectId", "payload"))
            .handle(
                WebFlux.outboundGateway("http://localhost:8080/myobject/{id}", webClient)
                        .httpMethod(HttpMethod.GET)
                        .uriVariable("id", "headers.myObjectId")
                        .mappedResponseHeaders()
                        .expectedResponseType(String.class)
            )
            .transform(Transformers.fromJson())
            .enrich( e -> e
                .requestSubFlow(
                    sf -> sf.handle(
                        WebFlux.outboundGateway("http://localhost:8080/myobject/{id}/secondary", webClient)
                            .httpMethod(HttpMethod.GET)
                            .uriVariable("id", "headers.myObjectId")
                            .mappedResponseHeaders()
                            .expectedResponseType(String.class)
                    )
                    .transform(Transformers.fromJson())
                )
                .propertyExpression("secondary", "payload.value")
            )
            .enrich( e -> e
                .requestSubFlow(
                    sf -> sf.handle(
                        WebFlux.outboundGateway("http://localhost:8080/myobject/{id}/tertiary", webClient)
                            .httpMethod(HttpMethod.GET)
                            .uriVariable("id", "headers.myObjectId")
                            .mappedResponseHeaders()
                            .expectedResponseType(String.class)
                    )
                    .transform(Transformers.fromJson())
                )
                .propertyExpression("tertiary", "payload.value")
            )
            .transform(Transformers.toJson())
            .aggregate()
            .logAndReply();
    }
}
