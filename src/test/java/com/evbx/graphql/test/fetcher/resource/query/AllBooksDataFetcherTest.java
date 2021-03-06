package com.evbx.graphql.test.fetcher.resource.query;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.assertj.core.api.Assertions;

import com.evbx.graphql.fetcher.resource.query.AllBooksDataFetcher;
import com.evbx.graphql.model.ItemData;
import com.evbx.graphql.model.resource.Book;
import com.evbx.graphql.test.BaseTest;
import com.evbx.graphql.util.JsonUtil;
import com.fasterxml.jackson.core.type.TypeReference;

import static com.evbx.graphql.test.support.Step.__GIVEN;
import static com.evbx.graphql.test.support.Step.__THEN;
import static com.evbx.graphql.test.support.Step.__WHEN;

class AllBooksDataFetcherTest extends BaseTest {

    @Autowired
    private AllBooksDataFetcher allBooksDataFetcher;

    private static final String ALL_BOOKS_JSON_STRING = "{\"items\":["
            + "{\"id\":1,\"bookName\":\"test-book-0\",\"description\":\"test-description-0\",\"text\":\"test-text-0\"},"
            + "{\"id\":2,\"bookName\":\"test-book-1\",\"description\":\"test-description-1\",\"text\":\"test-text-1\"},"
            + "{\"id\":3,\"bookName\":\"test-book-2\",\"description\":\"test-description-2\",\"text\":\"test-text-2\"}],"
            + "\"total\":3}";

    @Test
    void allBooksDataFetcherTest() {
        __GIVEN();
        stubWireMockServerForGet(resourceServiceConfig.getBooksPath(), ALL_BOOKS_JSON_STRING);
        __WHEN();
        ItemData<Book> data = allBooksDataFetcher.get(dataFetchingEnvironment);
        __THEN();
        Assertions.assertThat(data).isNotNull();
        Assertions.assertThat(data).isEqualToComparingFieldByField(JsonUtil.fromJson(ALL_BOOKS_JSON_STRING, new TypeReference<ItemData<Book>>() {}));
    }
}