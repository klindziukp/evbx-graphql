package com.evbx.graphql.test;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;

import com.evbx.graphql.config.ProductServiceConfig;
import com.evbx.graphql.config.ResourceServiceConfig;
import com.evbx.graphql.test.extension.WireMockInitializer;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = { WireMockInitializer.class })
@Execution(ExecutionMode.CONCURRENT)
public abstract class BaseTest {

    @Autowired
    protected WireMockServer wireMockServer;

    @Autowired
    protected ProductServiceConfig productServiceConfig;
    @Autowired
    protected ResourceServiceConfig resourceServiceConfig;

    @LocalServerPort
    protected Integer port;

    @AfterEach
    public void afterEach() {
        this.wireMockServer.resetAll();
    }

    protected void stubWireMockServer(String urlPath, String jsonString) {
        this.wireMockServer.stubFor(WireMock.get(urlPath).willReturn(
                aResponse().withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE).withBody(jsonString)));
    }
}
