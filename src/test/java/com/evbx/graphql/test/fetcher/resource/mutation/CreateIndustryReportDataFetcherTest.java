package com.evbx.graphql.test.fetcher.resource.mutation;

import java.util.HashMap;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.evbx.graphql.fetcher.resource.mutation.CreateIndustryReportDataFetcher;
import com.evbx.graphql.model.resource.IndustryReport;
import com.evbx.graphql.test.BaseTest;
import com.evbx.graphql.util.JsonUtil;

import static com.evbx.graphql.test.support.Step.__GIVEN;
import static com.evbx.graphql.test.support.Step.__THEN;
import static com.evbx.graphql.test.support.Step.__WHEN;
import static org.mockito.Mockito.when;

class CreateIndustryReportDataFetcherTest extends BaseTest {

    @Autowired
    private CreateIndustryReportDataFetcher createIndustryReportDataFetcher;

    private static final String REPORT_JSON_STRING
            = "{\"industryName\":\"industry-name-0\",\"description\":\"description-0\",\"text\":\"text-0\"}";

    private static final String ERROR_JSON_STRING = "{\"timestamp\":\"2020-04-23T09:12:40.749Z\",\"status\":400," +
            "\"error\":\"Bad Request\",\"errorMessages\":[\"'industryName' is mandatory field.\",\"'text' is mandatory field.\"," +
            "\"'description' is mandatory field.\"],\"message\":\"Validation failed\",\"path\":\"/v1/evbx/industry-reports\"}";

    @Test
    void createIndustryReportDataFetcherTest() {
        __GIVEN();
        when(dataFetchingEnvironment.getArgument("input")).thenReturn(inputMockMap());
        stubWireMockServerForPost(resourceServiceConfig.getIndustryReportPath(), REPORT_JSON_STRING);
        __WHEN();
        IndustryReport industryReport = createIndustryReportDataFetcher.get(dataFetchingEnvironment).getData();
        __THEN();
        Assertions.assertThat(industryReport).isNotNull();
        Assertions.assertThat(industryReport)
                .isEqualToComparingFieldByField(JsonUtil.fromJson(REPORT_JSON_STRING, IndustryReport.class));
    }

    @Test
    void createIndustryReportDataFetcherErrorTest() {
        __GIVEN();
        when(dataFetchingEnvironment.getArgument("input")).thenReturn(inputMockPostErrorMap());
        stubWireMockServerErrorForPost(resourceServiceConfig.getIndustryReportPath(), ERROR_JSON_STRING);
        __WHEN();
        String errorMessage = createIndustryReportDataFetcher.get(dataFetchingEnvironment).getErrors().get(0)
                .getMessage();
        __THEN();
        Assertions.assertThat(errorMessage)
                .isEqualTo("['industryName' is mandatory field., 'text' is mandatory field., 'description' is mandatory field.]");
    }

    private Map<String, Object> inputMockMap() {
        Map<String, Object> inputMap = new HashMap<>();
        inputMap.put("industryName", "industry-name-0");
        inputMap.put("description", "description-0");
        inputMap.put("text", "text-0");
        return inputMap;
    }
}