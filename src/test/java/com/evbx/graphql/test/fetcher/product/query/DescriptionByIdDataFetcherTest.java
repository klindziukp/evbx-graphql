package com.evbx.graphql.test.fetcher.product.query;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import com.evbx.graphql.fetcher.product.query.DescriptionByIdDataFetcher;
import com.evbx.graphql.model.Description;
import com.evbx.graphql.test.BaseTest;
import com.evbx.graphql.util.JsonUtil;

import graphql.schema.DataFetchingEnvironment;

import static com.evbx.graphql.test.support.Step.__GIVEN;
import static com.evbx.graphql.test.support.Step.__THEN;
import static com.evbx.graphql.test.support.Step.__WHEN;
import static org.mockito.Mockito.when;

class DescriptionByIdDataFetcherTest extends BaseTest {

    @Autowired
    private DescriptionByIdDataFetcher descriptionByIdDataFetcher;

    private static final String DESCRIPTION_JSON_STRING
            = "{\"id\":101,\"modelId\":1,\"descriptionLine\":\"Compatible with every EV\"}";

    @Test
    void descriptionByIdDataFetcherTest() {
        __GIVEN();
        DataFetchingEnvironment dataFetchingEnvironment = Mockito.mock(DataFetchingEnvironment.class);
        when(dataFetchingEnvironment.getArgument("id")).thenReturn("101");
        stubWireMockServerForGet(productServiceConfig.getDescriptionsPath() + dataFetchingEnvironment.getArgument("id"),
                DESCRIPTION_JSON_STRING);
        __WHEN();
        Description description = descriptionByIdDataFetcher.get(dataFetchingEnvironment);
        __THEN();
        Assertions.assertThat(description).isNotNull();
        Assertions.assertThat(description)
                .isEqualToComparingFieldByField(JsonUtil.fromJson(DESCRIPTION_JSON_STRING, Description.class));
    }
}