package com.evbx.graphql.test.fetcher.resource.mutation;

import java.util.HashMap;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import com.evbx.graphql.fetcher.resource.mutation.UpdateSpecificationDataFetcher;
import com.evbx.graphql.model.resource.Specification;
import com.evbx.graphql.test.BaseTest;
import com.evbx.graphql.util.JsonUtil;

import graphql.schema.DataFetchingEnvironment;

import static com.evbx.graphql.test.support.Step.__GIVEN;
import static com.evbx.graphql.test.support.Step.__THEN;
import static com.evbx.graphql.test.support.Step.__WHEN;
import static org.mockito.Mockito.when;

class UpdateSpecificationDataFetcherTest extends BaseTest {

    @Autowired
    private UpdateSpecificationDataFetcher updateSpecificationDataFetcher;

    private static final String SPEC_JSON_STRING
            = "{\"id\":100,\"specificationName\":\"spec-name-X\",\"description\":\"description-X\",\"text\":\"text-X\"}";

    @Test
    void updateSpecificationDataFetcherTest() {
        __GIVEN();
        DataFetchingEnvironment dataFetchingEnvironment = Mockito.mock(DataFetchingEnvironment.class);
        when(dataFetchingEnvironment.getArgument("input")).thenReturn(inputMockMap());
        stubWireMockServerForPatch(resourceServiceConfig.getSpecificationsPath() + inputMockMap().get("id"),
                SPEC_JSON_STRING);
        __WHEN();
        Specification specification = updateSpecificationDataFetcher.get(dataFetchingEnvironment).getData();
        __THEN();
        Assertions.assertThat(specification).isNotNull();
        Assertions.assertThat(specification)
                .isEqualToComparingFieldByField(JsonUtil.fromJson(SPEC_JSON_STRING, Specification.class));
    }

    private Map<String, Object> inputMockMap() {
        Map<String, Object> inputMap = new HashMap<>();
        inputMap.put("id", 100L);
        inputMap.put("specificationName", "spec-name-X");
        inputMap.put("description", "description-X");
        inputMap.put("text", "text-X");
        return inputMap;
    }
}