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

    private Map<String, Object> inputMockMap() {
        Map<String, Object> inputMap = new HashMap<>();
        inputMap.put("industryName", "industry-name-0");
        inputMap.put("description", "description-0");
        inputMap.put("text", "text-0");
        return inputMap;
    }
}