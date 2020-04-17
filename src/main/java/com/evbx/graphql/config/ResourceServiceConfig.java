package com.evbx.graphql.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Configuration properties for 'Resource' service
 */
@EqualsAndHashCode(callSuper = true)
@Configuration
@PropertySource("classpath:application.yml")
@Data
@ConfigurationProperties(prefix = "services.resource")
public class ResourceServiceConfig extends ServiceConfig {

    @Value("${booksPath}")
    private String booksPath;

    @Value("${specificationsPath}")
    private String specificationsPath;

    @Value("${industryReportPath}")
    private String industryReportPath;
}
