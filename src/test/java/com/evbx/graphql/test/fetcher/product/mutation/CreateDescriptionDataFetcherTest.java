package com.evbx.graphql.test.fetcher.product.mutation;

import java.util.HashMap;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.evbx.graphql.fetcher.product.mutation.CreateDescriptionDataFetcher;
import com.evbx.graphql.model.Description;
import com.evbx.graphql.test.BaseTest;
import com.evbx.graphql.util.JsonUtil;

import static com.evbx.graphql.test.support.Step.__GIVEN;
import static com.evbx.graphql.test.support.Step.__THEN;
import static com.evbx.graphql.test.support.Step.__WHEN;
import static org.mockito.Mockito.when;

class CreateDescriptionDataFetcherTest extends BaseTest {

    @Autowired
    private CreateDescriptionDataFetcher createDescriptionDataFetcher;

    private static final String DESCRIPTION_JSON_STRING = "{\"modelId\":1,\"descriptionLine\":\"Created desc line\"}";

    @Test
    void createDescriptionDataFetcherTest() {
        __GIVEN();
        when(dataFetchingEnvironment.getArgument("input")).thenReturn(inputMockMap());
        stubWireMockServerForPost(productServiceConfig.getDescriptionsPath(), DESCRIPTION_JSON_STRING);
        __WHEN();
        Description description = createDescriptionDataFetcher.get(dataFetchingEnvironment).getData();
        __THEN();
        Assertions.assertThat(description).isNotNull();
        Assertions.assertThat(description)
                .isEqualToComparingFieldByField(JsonUtil.fromJson(DESCRIPTION_JSON_STRING, Description.class));
    }

    private Map<String, Object> inputMockMap() {
        Map<String, Object> inputMap = new HashMap<>();
        inputMap.put("modelId", 1L);
        inputMap.put("descriptionLine", "Created desc line");
        return inputMap;
    }
}