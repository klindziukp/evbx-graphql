package com.evbx.graphql.test.fetcher.resource;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import com.evbx.graphql.fetcher.resource.query.SpecificationByIdDataFetcher;
import com.evbx.graphql.model.resource.Specification;
import com.evbx.graphql.test.BaseTest;
import com.evbx.graphql.util.JsonUtil;

import graphql.schema.DataFetchingEnvironment;

import static com.evbx.graphql.test.support.Step.__GIVEN;
import static com.evbx.graphql.test.support.Step.__THEN;
import static com.evbx.graphql.test.support.Step.__WHEN;
import static org.mockito.Mockito.when;

class SpecificationByIdDataFetcherTest extends BaseTest {

    @Autowired
    private SpecificationByIdDataFetcher specificationByIdDataFetcher;
    @Mock
    private DataFetchingEnvironment dataFetchingEnvironment;

    private static final String REPORT_JSON_STRING
            = "{\"id\":100,\"specificationName\":\"book-name-0\",\"description\":\"description-0\",\"text\":\"text-0\"}";

    @Test
    void industryReportByIdDataFetcherTest() {
        __GIVEN();
        DataFetchingEnvironment dataFetchingEnvironment = Mockito.mock(DataFetchingEnvironment.class);
        when(dataFetchingEnvironment.getArgument("id")).thenReturn("100");
        stubWireMockServer(resourceServiceConfig.getSpecificationsPath() + dataFetchingEnvironment.getArgument("id"),
                REPORT_JSON_STRING);
        __WHEN();
        Specification specification = specificationByIdDataFetcher.get(dataFetchingEnvironment);
        __THEN();
        Assertions.assertNotNull(specification);
        Assertions.assertEquals(specification, JsonUtil.fromJson(REPORT_JSON_STRING, Specification.class));
    }
}