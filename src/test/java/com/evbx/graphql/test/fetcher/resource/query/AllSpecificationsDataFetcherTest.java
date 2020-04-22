package com.evbx.graphql.test.fetcher.resource.query;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.evbx.graphql.fetcher.resource.query.AllSpecificationsDataFetcher;
import com.evbx.graphql.model.ItemData;
import com.evbx.graphql.model.resource.Specification;
import com.evbx.graphql.test.BaseTest;
import com.evbx.graphql.util.JsonUtil;
import com.fasterxml.jackson.core.type.TypeReference;

import static com.evbx.graphql.test.support.Step.__GIVEN;
import static com.evbx.graphql.test.support.Step.__THEN;
import static com.evbx.graphql.test.support.Step.__WHEN;

class AllSpecificationsDataFetcherTest extends BaseTest {

    @Autowired
    private AllSpecificationsDataFetcher allSpecificationsDataFetcher;

    private static final String ALL_SPECS_JSON_STRING = "{\"items\":["
            + "{\"id\":1,\"specificationName\":\"spec-name-0\",\"description\":\"test-description-0\",\"text\":\"test-text-0\"},"
            + "{\"id\":2,\"specificationName\":\"spec-name-1\",\"description\":\"test-description-1\",\"text\":\"test-text-1\"},"
            + "{\"id\":3,\"specificationName\":\"spec-name-2\",\"description\":\"test-description-2\",\"text\":\"test-text-2\"}],"
            + "\"total\":3}";

    @Test
    void allSpecificationsDataFetcherTest() {
        __GIVEN();
        stubWireMockServerForGet(resourceServiceConfig.getSpecificationsPath(), ALL_SPECS_JSON_STRING);
        __WHEN();
        ItemData<Specification> data = allSpecificationsDataFetcher.get(dataFetchingEnvironment);
        __THEN();
        Assertions.assertThat(data).isNotNull();
        Assertions.assertThat(data).isEqualToComparingFieldByField(
                JsonUtil.fromJson(ALL_SPECS_JSON_STRING, new TypeReference<ItemData<Specification>>() {}));
    }
}