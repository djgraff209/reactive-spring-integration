package com.oneangrybean.prototype.reactivespringintegration.handler;

import java.util.List;

import com.oneangrybean.prototype.reactivespringintegration.integrationflow.IntegrationFlowConfig.MyObjectInformationGateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class IntegrationFlowController {
    private static final Logger LOGGER = LoggerFactory.getLogger(IntegrationFlowController.class);

    private MyObjectInformationGateway myObjectInformationGateway;
    public IntegrationFlowController(MyObjectInformationGateway myObjectInformationGateway) {
        this.myObjectInformationGateway = myObjectInformationGateway;
    }

    @GetMapping("/integration-flow")
    public Mono<List<String>> runIntegrationFlow() {
        // Run Integration Flow here
        LOGGER.info("Calling integration flow");
        final Mono<List<String>> result = myObjectInformationGateway.readMyObjects();
        return result;
    }
}
