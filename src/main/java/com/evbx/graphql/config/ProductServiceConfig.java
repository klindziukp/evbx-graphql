package com.evbx.graphql.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Configuration properties for 'Product' service
 */
@EqualsAndHashCode(callSuper = true)
@Configuration
@PropertySource(value = "classpath:application.yml", ignoreResourceNotFound = true)
@PropertySource(value = "classpath:application-docker.yml", ignoreResourceNotFound = true)
@Data
@ConfigurationProperties(prefix = "services.product")
public class ProductServiceConfig extends ServiceConfig {

    @Value("${productsPath}")
    private String productsPath;

    @Value("${productModelsPath}")
    private String productModelsPath;

    @Value("${descriptionsPath}")
    private String descriptionsPath;
}
