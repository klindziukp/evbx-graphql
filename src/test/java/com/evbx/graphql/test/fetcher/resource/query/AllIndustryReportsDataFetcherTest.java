package com.evbx.graphql.test.fetcher.resource.query;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.evbx.graphql.fetcher.resource.query.AllIndustryReportsDataFetcher;
import com.evbx.graphql.model.ItemData;
import com.evbx.graphql.model.resource.IndustryReport;
import com.evbx.graphql.test.BaseTest;
import com.evbx.graphql.util.JsonUtil;
import com.fasterxml.jackson.core.type.TypeReference;

import static com.evbx.graphql.test.support.Step.__GIVEN;
import static com.evbx.graphql.test.support.Step.__THEN;
import static com.evbx.graphql.test.support.Step.__WHEN;

class AllIndustryReportsDataFetcherTest extends BaseTest {

    @Autowired
    private AllIndustryReportsDataFetcher allIndustryReportsDataFetcher;

    private static final String ALL_REPORTS_JSON_STRING = "{\"items\":["
            + "{\"id\":1,\"industryName\":\"test-name-0\",\"description\":\"test-description-0\",\"text\":\"test-text-0\"},"
            + "{\"id\":2,\"industryName\":\"test-name-1\",\"description\":\"test-description-1\",\"text\":\"test-text-1\"},"
            + "{\"id\":3,\"industryName\":\"test-name-2\",\"description\":\"test-description-2\",\"text\":\"test-text-2\"}],"
            + "\"total\":3}";

    @Test
    void allIndustryReportsDataFetcherTest() {
        __GIVEN();
        stubWireMockServerForGet(resourceServiceConfig.getIndustryReportPath(), ALL_REPORTS_JSON_STRING);
        __WHEN();
        ItemData<IndustryReport> data = allIndustryReportsDataFetcher.get(dataFetchingEnvironment);
        __THEN();
        Assertions.assertThat(data).isNotNull();
        Assertions.assertThat(data).isEqualToComparingFieldByField(
                JsonUtil.fromJson(ALL_REPORTS_JSON_STRING, new TypeReference<ItemData<IndustryReport>>() {}));
    }
}