package com.evbx.graphql.test.fetcher.product.mutation;

import java.util.HashMap;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.evbx.graphql.fetcher.product.mutation.UpdateDescriptionDataFetcher;
import com.evbx.graphql.model.Description;
import com.evbx.graphql.test.BaseTest;
import com.evbx.graphql.util.JsonUtil;

import static com.evbx.graphql.test.support.Step.__GIVEN;
import static com.evbx.graphql.test.support.Step.__THEN;
import static com.evbx.graphql.test.support.Step.__WHEN;
import static org.mockito.Mockito.when;

class UpdateDescriptionDataFetcherTest extends BaseTest {

    @Autowired
    private UpdateDescriptionDataFetcher updateDescriptionDataFetcher;

    private static final String DESCRIPTION_JSON_STRING
            = "{\"id\":101,\"modelId\":101,\"descriptionLine\":\"Patched Desc line\"}";

    @Test
    void updateDescriptionDataFetcherTest() {
        __GIVEN();
        when(dataFetchingEnvironment.getArgument("input")).thenReturn(inputMockMap());
        stubWireMockServerForPatch(productServiceConfig.getDescriptionsPath() + inputMockMap().get("id"),
                DESCRIPTION_JSON_STRING);
        __WHEN();
        Description description = updateDescriptionDataFetcher.get(dataFetchingEnvironment).getData();
        __THEN();
        Assertions.assertThat(description).isNotNull();
        Assertions.assertThat(description)
                .isEqualToComparingFieldByField(JsonUtil.fromJson(DESCRIPTION_JSON_STRING, Description.class));
    }

    private Map<String, Object> inputMockMap() {
        Map<String, Object> inputMap = new HashMap<>();
        inputMap.put("id", 101L);
        inputMap.put("modelId", 101L);
        inputMap.put("descriptionLine", "Patched Desc line");
        return inputMap;
    }
}