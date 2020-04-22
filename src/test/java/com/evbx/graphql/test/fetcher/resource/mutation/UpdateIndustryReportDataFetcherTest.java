package com.evbx.graphql.test.fetcher.resource.mutation;

import java.util.HashMap;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import com.evbx.graphql.fetcher.resource.mutation.UpdateIndustryReportDataFetcher;
import com.evbx.graphql.model.resource.IndustryReport;
import com.evbx.graphql.test.BaseTest;
import com.evbx.graphql.util.JsonUtil;

import graphql.schema.DataFetchingEnvironment;

import static com.evbx.graphql.test.support.Step.__GIVEN;
import static com.evbx.graphql.test.support.Step.__THEN;
import static com.evbx.graphql.test.support.Step.__WHEN;
import static org.mockito.Mockito.when;

class UpdateIndustryReportDataFetcherTest extends BaseTest {

    @Autowired
    private UpdateIndustryReportDataFetcher updateIndustryReportDataFetcher;

    private static final String REPORT_JSON_STRING
            = "{\"id\":100,\"industryName\":\"industry-name-X\",\"description\":\"description-X\",\"text\":\"text-X\"}";

    @Test
    void updateIndustryReportDataFetcherTest() {
        __GIVEN();
        DataFetchingEnvironment dataFetchingEnvironment = Mockito.mock(DataFetchingEnvironment.class);
        when(dataFetchingEnvironment.getArgument("input")).thenReturn(inputMockMap());
        stubWireMockServerForPatch(resourceServiceConfig.getIndustryReportPath() + inputMockMap().get("id"),
                REPORT_JSON_STRING);
        __WHEN();
        IndustryReport industryReport = updateIndustryReportDataFetcher.get(dataFetchingEnvironment).getData();
        __THEN();
        Assertions.assertThat(industryReport).isNotNull();
        Assertions.assertThat(industryReport)
                .isEqualToComparingFieldByField(JsonUtil.fromJson(REPORT_JSON_STRING, IndustryReport.class));
    }

    private Map<String, Object> inputMockMap() {
        Map<String, Object> inputMap = new HashMap<>();
        inputMap.put("id", 100L);
        inputMap.put("industryName", "industry-name-X");
        inputMap.put("description", "description-X");
        inputMap.put("text", "text-X");
        return inputMap;
    }
}