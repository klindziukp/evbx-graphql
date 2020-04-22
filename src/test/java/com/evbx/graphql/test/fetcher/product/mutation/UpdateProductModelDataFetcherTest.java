package com.evbx.graphql.test.fetcher.product.mutation;

import java.util.HashMap;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.evbx.graphql.fetcher.product.mutation.UpdateProductModelDataFetcher;
import com.evbx.graphql.model.ProductModelDto;
import com.evbx.graphql.test.BaseTest;
import com.evbx.graphql.util.JsonUtil;

import static com.evbx.graphql.test.support.Step.__GIVEN;
import static com.evbx.graphql.test.support.Step.__THEN;
import static com.evbx.graphql.test.support.Step.__WHEN;
import static org.mockito.Mockito.when;

class UpdateProductModelDataFetcherTest extends BaseTest {

    @Autowired
    private UpdateProductModelDataFetcher updateProductModelDataFetcher;

    private static final String PRODUCT_MODEL_JSON_STRING = "{\"id\":102,\"modelName\":\"Patched model name\"}";

    @Test
    void updateProductModelDataFetcherTest() {
        __GIVEN();
        when(dataFetchingEnvironment.getArgument("input")).thenReturn(inputMockMap());
        stubWireMockServerForPatch(productServiceConfig.getProductModelsPath() + inputMockMap().get("id"),
                PRODUCT_MODEL_JSON_STRING);
        __WHEN();
        ProductModelDto productModelDto = updateProductModelDataFetcher.get(dataFetchingEnvironment).getData();
        __THEN();
        Assertions.assertThat(productModelDto).isNotNull();
        Assertions.assertThat(productModelDto)
                .isEqualToComparingFieldByField(JsonUtil.fromJson(PRODUCT_MODEL_JSON_STRING, ProductModelDto.class));
    }

    private Map<String, Object> inputMockMap() {
        Map<String, Object> inputMap = new HashMap<>();
        inputMap.put("id", 102L);
        inputMap.put("modelName", "Patched model name");
        return inputMap;
    }
}