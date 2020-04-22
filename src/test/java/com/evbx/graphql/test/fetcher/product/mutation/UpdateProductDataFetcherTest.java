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

    private Map<String, Object> inputMockMap() {
        Map<String, Object> inputMap = new HashMap<>();
        inputMap.put("id", 103L);
        inputMap.put("productName", "Patched product name");
        return inputMap;
    }
}