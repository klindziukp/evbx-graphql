package com.evbx.graphql.config;

import org.springframework.beans.factory.annotation.Value;

import lombok.Data;

/**
 * Base services configuration properties for 'REST Services'
 */
@Data
public class ServiceConfig {

    @Value("${baseUrl}")
    private String baseUrl;
    @Value("${username}")
    private String userName;
    @Value("${password}")
    private String password;
}
