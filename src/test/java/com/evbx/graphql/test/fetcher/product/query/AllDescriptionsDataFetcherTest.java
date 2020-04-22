package com.evbx.graphql.test.fetcher.product.query;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.evbx.graphql.fetcher.product.query.AllDescriptionsDataFetcher;
import com.evbx.graphql.model.Description;
import com.evbx.graphql.model.ItemData;
import com.evbx.graphql.test.BaseTest;
import com.evbx.graphql.util.JsonUtil;
import com.fasterxml.jackson.core.type.TypeReference;

import static com.evbx.graphql.test.support.Step.__GIVEN;
import static com.evbx.graphql.test.support.Step.__THEN;
import static com.evbx.graphql.test.support.Step.__WHEN;

class AllDescriptionsDataFetcherTest extends BaseTest {

    @Autowired
    private AllDescriptionsDataFetcher allDescriptionsDataFetcher;

    private static final String ALL_DESCRIPTIONS_JSON_STRING = "{\"items\":[" +
            "{\"id\":1,\"modelId\":1,\"descriptionLine\":\"desc-line-1\"}," +
            "{\"id\":2,\"modelId\":1,\"descriptionLine\":\"desc-line-2\"}," +
            "{\"id\":3,\"modelId\":1,\"descriptionLine\":\"desc-line-3\"}]," + "\"total\":3}";

    @Test
    void allDescriptionsDataFetcherTest() {
        __GIVEN();
        stubWireMockServerForGet(productServiceConfig.getDescriptionsPath(), ALL_DESCRIPTIONS_JSON_STRING);
        __WHEN();
        ItemData<Description> data = allDescriptionsDataFetcher.get(dataFetchingEnvironment);
        __THEN();
        Assertions.assertThat(data).isNotNull();
        Assertions.assertThat(data).isEqualToComparingFieldByField(
                JsonUtil.fromJson(ALL_DESCRIPTIONS_JSON_STRING, new TypeReference<ItemData<Description>>() {}));
    }
}