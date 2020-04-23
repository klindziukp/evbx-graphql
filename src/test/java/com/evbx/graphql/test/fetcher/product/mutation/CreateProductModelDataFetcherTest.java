package com.evbx.graphql.test.fetcher.product.mutation;

import java.util.HashMap;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.evbx.graphql.fetcher.product.mutation.CreateProductModelDataFetcher;
import com.evbx.graphql.model.ProductModelDto;
import com.evbx.graphql.test.BaseTest;
import com.evbx.graphql.util.JsonUtil;

import static com.evbx.graphql.test.support.Step.__GIVEN;
import static com.evbx.graphql.test.support.Step.__THEN;
import static com.evbx.graphql.test.support.Step.__WHEN;
import static org.mockito.Mockito.when;

class CreateProductModelDataFetcherTest extends BaseTest {

    @Autowired
    private CreateProductModelDataFetcher createProductModelDataFetcher;

    private static final String PRODUCT_MODEL_JSON_STRING = "{\"modelName\":\"Created model name\"}";

    private static final String ERROR_JSON_STRING = "{\"timestamp\":\"2020-04-23T09:04:58.967Z\"," +
            "\"status\":400,\"error\":\"Bad Request\",\"errorMessages\":[\"'modelName' is mandatory field.\"," +
            "\"'productId' is mandatory field.\"],\"message\":\"Validation failed\",\"path\":\"/v1/evbx/product-models\"}";

    @Test
    void createProductModelDataFetcherTest() {
        __GIVEN();
        when(dataFetchingEnvironment.getArgument("input")).thenReturn(inputMockMap());
        stubWireMockServerForPost(productServiceConfig.getProductModelsPath(), PRODUCT_MODEL_JSON_STRING);
        __WHEN();
        ProductModelDto productModelDto = createProductModelDataFetcher.get(dataFetchingEnvironment).getData();
        __THEN();
        Assertions.assertThat(productModelDto).isNotNull();
        Assertions.assertThat(productModelDto)
                .isEqualToComparingFieldByField(JsonUtil.fromJson(PRODUCT_MODEL_JSON_STRING, ProductModelDto.class));
    }

    @Test
    void createProductDataFetcherErrorTest() {
        __GIVEN();
        when(dataFetchingEnvironment.getArgument("input")).thenReturn(inputMockPostErrorMap());
        stubWireMockServerErrorForPost(productServiceConfig.getProductModelsPath(), ERROR_JSON_STRING);
        __WHEN();
        String errorMessage = createProductModelDataFetcher.get(dataFetchingEnvironment).getErrors().get(0).getMessage();
        __THEN();
        Assertions.assertThat(errorMessage)
                .isEqualTo("['modelName' is mandatory field., 'productId' is mandatory field.]");
    }

    private Map<String, Object> inputMockMap() {
        Map<String, Object> inputMap = new HashMap<>();
        inputMap.put("modelName", "Created model name");
        return inputMap;
    }
}