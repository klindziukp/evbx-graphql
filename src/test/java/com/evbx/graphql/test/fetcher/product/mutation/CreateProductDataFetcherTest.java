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

    private Map<String, Object> inputMockMap() {
        Map<String, Object> inputMap = new HashMap<>();
        inputMap.put("productName", "Created product name");
        return inputMap;
    }
}