package com.evbx.graphql.test.fetcher.resource.query;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.evbx.graphql.fetcher.resource.query.IndustryReportByIdDataFetcher;
import com.evbx.graphql.model.resource.IndustryReport;
import com.evbx.graphql.test.BaseTest;
import com.evbx.graphql.util.JsonUtil;

import static com.evbx.graphql.test.support.Step.__GIVEN;
import static com.evbx.graphql.test.support.Step.__THEN;
import static com.evbx.graphql.test.support.Step.__WHEN;
import static org.mockito.Mockito.when;

class IndustryReportByIdDataFetcherTest extends BaseTest {

    @Autowired
    private IndustryReportByIdDataFetcher industryReportByIdDataFetcher;

    private static final String REPORT_JSON_STRING
            = "{\"id\":100,\"industryName\":\"industry-name-0\",\"description\":\"description-0\",\"text\":\"text-0\"}";

    @Test
    void industryReportByIdDataFetcherTest() {
        __GIVEN();
        when(dataFetchingEnvironment.getArgument("id")).thenReturn("100");
        stubWireMockServerForGet(resourceServiceConfig.getIndustryReportPath() + dataFetchingEnvironment.getArgument("id"),
                REPORT_JSON_STRING);
        __WHEN();
        IndustryReport industryReport = industryReportByIdDataFetcher.get(dataFetchingEnvironment);
        __THEN();
        Assertions.assertThat(industryReport).isNotNull();
        Assertions.assertThat(industryReport)
                .isEqualToComparingFieldByField(JsonUtil.fromJson(REPORT_JSON_STRING, IndustryReport.class));
    }
}