package com.evbx.graphql.apiclient;

import java.util.Objects;

import com.evbx.graphql.config.ServiceConfig;
import com.evbx.graphql.exception.ApiClientMappingException;
import com.evbx.graphql.model.ErrorResponse;
import com.evbx.graphql.util.JsonUtil;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

public final class ApiClient {

    private static final Logger LOGGER = LogManager.getLogger(ApiClient.class);
    private final RestTemplate restTemplate;
    private final ServiceConfig serviceConfig;

    public ApiClient(ServiceConfig serviceConfig) {
        this.serviceConfig = serviceConfig;
        this.restTemplate = buildRestTemplate(serviceConfig);
    }

    /**
     * Method to execute GET request against a URI.
     *
     * @param path     the URI template representing the path to request
     * @param classOfT the type of object that will be returned in the response body
     * @param <T>      the type of object in the body
     * @return Instance of T
     */
    public <T> T get(String path, Class<T> classOfT) {
        try {
            LOGGER.info("GET {}{}", serviceConfig.getBaseUrl(), path);
            return restTemplate.exchange(path, HttpMethod.GET, null, classOfT).getBody();
        } catch (HttpStatusCodeException httpEx) {
            return getErrorOfT(httpEx);
        }
    }

    /**
     * Method to execute GET request against a URI.
     *
     * @param path                       the URI template representing the path to request
     * @param parameterizedTypeReference the type of object that will be returned in the response body
     * @param <T>                        the type of object in the body
     * @return Instance of T
     */
    public <T> T get(String path, ParameterizedTypeReference<T> parameterizedTypeReference) {
        try {
            LOGGER.info("GET {}{}", serviceConfig.getBaseUrl(), path);
            return restTemplate.exchange(path, HttpMethod.GET, null, parameterizedTypeReference).getBody();
        } catch (HttpStatusCodeException httpEx) {
            return getErrorOfT(httpEx);
        }
    }

    /**
     * Method to execute POST request against a URI.
     *
     * @param path        the URI template representing the path to request
     * @param classOfT    the type of object that will be returned in the response body
     * @param requestBody request body
     * @param <T>         the type of object in the body
     * @return Instance of T
     */
    public <T> T post(String path, Object requestBody, Class<T> classOfT) throws ApiClientMappingException {
        try {
            LOGGER.info("POST {}{}, BODY:{}", serviceConfig.getBaseUrl(), path, requestBody);
            return restTemplate.postForEntity(path, requestBody, classOfT).getBody();
        } catch (HttpStatusCodeException httpEx) {
            throwUpdatedException(httpEx);
        }
        throw new ApiClientMappingException("Unable to execute POST request to: " + path);
    }

    /**
     * Method to execute POST request against a URI.
     *
     * @param path        the URI template representing the path to request
     * @param classOfT    the type of object that will be returned in the response body
     * @param requestBody request body
     * @param <T>         the type of object in the body
     * @return Instance of T
     */
    public <T> T patch(String path, Object requestBody, Class<T> classOfT) throws ApiClientMappingException {
        try {
            LOGGER.info("PATCH {}{}, BODY:{}", serviceConfig.getBaseUrl(), path, requestBody);
            return restTemplate.patchForObject(path, requestBody, classOfT);
        } catch (HttpStatusCodeException httpEx) {
            throwUpdatedException(httpEx);
        }
        throw new ApiClientMappingException("Unable to execute POST request to: " + path);
    }

    /**
     * Method to handle  HttpStatusCodeException.
     *
     * @param httpEx HttpStatusCodeException
     * @param <T>    the type of object in the body
     * @return NULL as fail-safe
     */
    private <T> T getErrorOfT(HttpStatusCodeException httpEx) {
        String errorBody = httpEx.getResponseBodyAsString();
        ErrorResponse errorResponse = JsonUtil.fromJson(errorBody, ErrorResponse.class);
        LOGGER.warn("WARN Error appeared during request execution to '{}, ERROR: {}", errorResponse.getPath(),
                errorResponse.getErrorMessages());
        return null;
    }

    /**
     * Method to update handle HttpStatusCodeException.
     *
     * @param httpEx HttpStatusCodeException
     */
    private void throwUpdatedException(HttpStatusCodeException httpEx) throws ApiClientMappingException {
        String errorBody = httpEx.getResponseBodyAsString();
        ErrorResponse errorResponse = JsonUtil.fromJson(errorBody, ErrorResponse.class);
        String logErrorMessage = getLogErrorMessage(errorResponse);
        LOGGER.warn("WARN Error appeared during request execution to '{}, ERROR: {}'", errorResponse.getPath(),
                logErrorMessage);
        throw new ApiClientMappingException(logErrorMessage);
    }

    private String getLogErrorMessage(ErrorResponse errorResponse) {
        if (Objects.nonNull(errorResponse.getErrorMessages())) {
            return errorResponse.getErrorMessages().toString();
        }
        return errorResponse.getMessage();
    }

    private RestTemplate buildRestTemplate(ServiceConfig serviceConfig) {
        CloseableHttpClient closeableHttpClient = closeableHttpClient(requestConfig());
        return new RestTemplateBuilder()
                .requestFactory(() -> new HttpComponentsClientHttpRequestFactory(closeableHttpClient))
                .rootUri(serviceConfig.getBaseUrl())
                .basicAuthentication(serviceConfig.getUserName(), serviceConfig.getPassword()).build();
    }

    private CloseableHttpClient closeableHttpClient(RequestConfig requestConfig) {
        return HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
    }

    private RequestConfig requestConfig() {
        int timeoutInMs = 3000;
        return RequestConfig.custom().setConnectTimeout(timeoutInMs).setConnectionRequestTimeout(timeoutInMs)
                .setSocketTimeout(timeoutInMs).build();
    }
}
