package com.evbx.graphql.test.fetcher.resource.mutation;

import java.util.HashMap;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.evbx.graphql.fetcher.resource.mutation.CreateSpecificationDataFetcher;
import com.evbx.graphql.model.resource.Specification;
import com.evbx.graphql.test.BaseTest;
import com.evbx.graphql.util.JsonUtil;

import static com.evbx.graphql.test.support.Step.__GIVEN;
import static com.evbx.graphql.test.support.Step.__THEN;
import static com.evbx.graphql.test.support.Step.__WHEN;
import static org.mockito.Mockito.when;

class CreateSpecificationDataFetcherTest extends BaseTest {

    @Autowired
    private CreateSpecificationDataFetcher createSpecificationDataFetcher;

    private static final String SPEC_JSON_STRING
            = "{\"specificationName\":\"spec-name-0\",\"description\":\"description-0\",\"text\":\"text-0\"}";

    private static final String ERROR_JSON_STRING = "{\"timestamp\":\"2020-04-23T09:11:48.537Z\",\"status\":400," +
            "\"error\":\"Bad Request\",\"errorMessages\":[\"'description' is mandatory field.\"," +
            "\"'specificationName' is mandatory field.\",\"'text' is mandatory field.\"]," +
            "\"message\":\"Validation failed\",\"path\":\"/v1/evbx/specifications\"}";

    @Test
    void createSpecificationDataFetcherTest() {
        __GIVEN();
        when(dataFetchingEnvironment.getArgument("input")).thenReturn(inputMockMap());
        stubWireMockServerForPost(resourceServiceConfig.getSpecificationsPath(), SPEC_JSON_STRING);
        __WHEN();
        Specification specification = createSpecificationDataFetcher.get(dataFetchingEnvironment).getData();
        __THEN();
        Assertions.assertThat(specification).isNotNull();
        Assertions.assertThat(specification)
                .isEqualToComparingFieldByField(JsonUtil.fromJson(SPEC_JSON_STRING, Specification.class));
    }

    @Test
    void createSpecificationDataFetcherErrorTest() {
        __GIVEN();
        when(dataFetchingEnvironment.getArgument("input")).thenReturn(inputMockPostErrorMap());
        stubWireMockServerErrorForPost(resourceServiceConfig.getSpecificationsPath(), ERROR_JSON_STRING);
        __WHEN();
        String errorMessage = createSpecificationDataFetcher.get(dataFetchingEnvironment).getErrors().get(0)
                .getMessage();
        __THEN();
        Assertions.assertThat(errorMessage).isEqualTo(
                "['description' is mandatory field., 'specificationName' is mandatory field., 'text' is mandatory field.]");
    }

    private Map<String, Object> inputMockMap() {
        Map<String, Object> inputMap = new HashMap<>();
        inputMap.put("specificationName", "spec-name-0");
        inputMap.put("description", "description-0");
        inputMap.put("text", "text-0");
        return inputMap;
    }
}