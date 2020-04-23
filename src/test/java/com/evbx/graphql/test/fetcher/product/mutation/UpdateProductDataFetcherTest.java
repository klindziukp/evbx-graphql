package com.evbx.graphql.test.fetcher.product.mutation;

import java.util.HashMap;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.evbx.graphql.fetcher.product.mutation.UpdateProductDataFetcher;
import com.evbx.graphql.model.ProductDto;
import com.evbx.graphql.test.BaseTest;
import com.evbx.graphql.util.JsonUtil;

import static com.evbx.graphql.test.support.Step.__GIVEN;
import static com.evbx.graphql.test.support.Step.__THEN;
import static com.evbx.graphql.test.support.Step.__WHEN;
import static org.mockito.Mockito.when;

class UpdateProductDataFetcherTest extends BaseTest {

    @Autowired
    private UpdateProductDataFetcher updateProductDataFetcher;

    private static final String PRODUCT_JSON_STRING = "{\"id\":103,\"productName\":\"Patched product name\"}";

    private static final String ERROR_JSON_STRING = "{\"timestamp\":\"2020-04-23T07:36:16.080+0000\"," +
            "\"status\":404,\"error\":\"Not Found\"," +
            "\"message\":\"'Product' item not found with id = 777\",\"path\":\"/v1/evbx/products/777\"}";

    @Test
    void productByIdDataFetcherTest() {
        __GIVEN();
        when(dataFetchingEnvironment.getArgument("input")).thenReturn(inputMockMap());
        stubWireMockServerForPatch(productServiceConfig.getProductsPath() + inputMockMap().get("id"),
                PRODUCT_JSON_STRING);
        __WHEN();
        ProductDto productDto = updateProductDataFetcher.get(dataFetchingEnvironment).getData();
        __THEN();
        Assertions.assertThat(productDto).isNotNull();
        Assertions.assertThat(productDto)
                .isEqualToComparingFieldByField(JsonUtil.fromJson(PRODUCT_JSON_STRING, ProductDto.class));
    }

    @Test
    void updateProductDataFetcherErrorTest() {
        __GIVEN();
        when(dataFetchingEnvironment.getArgument("input")).thenReturn(inputMockErrorMap());
        stubWireMockServerErrorForPatch(productServiceConfig.getProductsPath() + inputMockErrorMap().get("id"),
                ERROR_JSON_STRING);
        __WHEN();
        String errorMessage = updateProductDataFetcher.get(dataFetchingEnvironment).getErrors().get(0).getMessage();
        __THEN();
        Assertions.assertThat(errorMessage).isEqualTo("'Product' item not found with id = 777");
    }

    private Map<String, Object> inputMockMap() {
        Map<String, Object> inputMap = new HashMap<>();
        inputMap.put("id", 103L);
        inputMap.put("productName", "Patched product name");
        return inputMap;
    }

    private Map<String, Object> inputMockErrorMap() {
        Map<String, Object> inputMap = new HashMap<>();
        inputMap.put("id", 777L);
        return inputMap;
    }
}