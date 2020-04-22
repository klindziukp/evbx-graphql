package com.evbx.graphql.test.fetcher.resource.query;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.evbx.graphql.fetcher.resource.query.BookByIdDataFetcher;
import com.evbx.graphql.model.resource.Book;
import com.evbx.graphql.test.BaseTest;
import com.evbx.graphql.util.JsonUtil;

import static com.evbx.graphql.test.support.Step.__GIVEN;
import static com.evbx.graphql.test.support.Step.__THEN;
import static com.evbx.graphql.test.support.Step.__WHEN;
import static org.mockito.Mockito.when;

class BookByIdDataFetcherTest extends BaseTest {

    @Autowired
    private BookByIdDataFetcher bookByIdDataFetcher;

    private static final String BOOK_JSON_STRING
            = "{\"id\":100,\"bookName\":\"book-name-0\",\"description\":\"description-0\",\"text\":\"text-0\"}";

    @Test
    void bookByIdDataFetcher() {
        __GIVEN();
        when(dataFetchingEnvironment.getArgument("id")).thenReturn("100");
        stubWireMockServerForGet(resourceServiceConfig.getBooksPath() + dataFetchingEnvironment.getArgument("id"),
                BOOK_JSON_STRING);
        __WHEN();
        Book book = bookByIdDataFetcher.get(dataFetchingEnvironment);
        __THEN();
        Assertions.assertThat(book).isNotNull();
        Assertions.assertThat(book).isEqualToComparingFieldByField(JsonUtil.fromJson(BOOK_JSON_STRING, Book.class));
    }
}