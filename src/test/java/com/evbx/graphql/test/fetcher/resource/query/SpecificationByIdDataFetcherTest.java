package com.evbx.graphql.test.fetcher.resource.query;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.evbx.graphql.fetcher.resource.query.SpecificationByIdDataFetcher;
import com.evbx.graphql.model.resource.Specification;
import com.evbx.graphql.test.BaseTest;
import com.evbx.graphql.util.JsonUtil;

import static com.evbx.graphql.test.support.Step.__GIVEN;
import static com.evbx.graphql.test.support.Step.__THEN;
import static com.evbx.graphql.test.support.Step.__WHEN;
import static org.mockito.Mockito.when;

class SpecificationByIdDataFetcherTest extends BaseTest {

    @Autowired
    private SpecificationByIdDataFetcher specificationByIdDataFetcher;

    private static final String SPEC_JSON_STRING
            = "{\"id\":100,\"specificationName\":\"book-name-0\",\"description\":\"description-0\",\"text\":\"text-0\"}";

    @Test
    void specificationByIdDataFetcherTest() {
        __GIVEN();
        when(dataFetchingEnvironment.getArgument("id")).thenReturn("100");
        stubWireMockServerForGet(resourceServiceConfig.getSpecificationsPath() + dataFetchingEnvironment.getArgument("id"),
                SPEC_JSON_STRING);
        __WHEN();
        Specification specification = specificationByIdDataFetcher.get(dataFetchingEnvironment);
        __THEN();
        Assertions.assertThat(specification).isNotNull();
        Assertions.assertThat(specification)
                .isEqualToComparingFieldByField(JsonUtil.fromJson(SPEC_JSON_STRING, Specification.class));
    }
}