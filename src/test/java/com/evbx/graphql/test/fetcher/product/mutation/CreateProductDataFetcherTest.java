package com.evbx.graphql.test.fetcher.product.mutation;

import java.util.HashMap;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.evbx.graphql.fetcher.product.mutation.CreateProductDataFetcher;
import com.evbx.graphql.model.ProductDto;
import com.evbx.graphql.test.BaseTest;
import com.evbx.graphql.util.JsonUtil;

import static com.evbx.graphql.test.support.Step.__GIVEN;
import static com.evbx.graphql.test.support.Step.__THEN;
import static com.evbx.graphql.test.support.Step.__WHEN;
import static org.mockito.Mockito.when;

class CreateProductDataFetcherTest extends BaseTest {

    @Autowired
    private CreateProductDataFetcher createProductDataFetcher;

    private static final String PRODUCT_JSON_STRING = "{\"productName\":\"product-name-1\"}";

    private static final String ERROR_JSON_STRING = "{\"timestamp\":\"2020-04-23T09:02:37.107Z\",\"status\":400," +
            "\"error\":\"Bad Request\",\"errorMessages\":[\"'productName' is mandatory field.\"]," +
            "\"message\":\"Validation failed\",\"path\":\"/v1/evbx/products\"}";

    @Test
    void createProductDataFetcherTest() {
        __GIVEN();
        when(dataFetchingEnvironment.getArgument("input")).thenReturn(inputMockMap());
        stubWireMockServerForPost(productServiceConfig.getProductsPath(), PRODUCT_JSON_STRING);
        __WHEN();
        ProductDto productDto = createProductDataFetcher.get(dataFetchingEnvironment).getData();
        __THEN();
        Assertions.assertThat(productDto).isNotNull();
        Assertions.assertThat(productDto)
                .isEqualToComparingFieldByField(JsonUtil.fromJson(PRODUCT_JSON_STRING, ProductDto.class));
    }

    @Test
    void createProductDataFetcherErrorTest() {
        __GIVEN();
        when(dataFetchingEnvironment.getArgument("input")).thenReturn(inputMockPostErrorMap());
        stubWireMockServerErrorForPost(productServiceConfig.getProductsPath(), ERROR_JSON_STRING);
        __WHEN();
        String errorMessage = createProductDataFetcher.get(dataFetchingEnvironment).getErrors().get(0).getMessage();
        __THEN();
        Assertions.assertThat(errorMessage)
                .isEqualTo("['productName' is mandatory field.]");
    }

    private Map<String, Object> inputMockMap() {
        Map<String, Object> inputMap = new HashMap<>();
        inputMap.put("productName", "Created product name");
        return inputMap;
    }
}