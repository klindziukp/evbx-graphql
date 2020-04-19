package com.evbx.graphql.test.fetcher.resource;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import com.evbx.graphql.fetcher.resource.query.BookByIdDataFetcher;
import com.evbx.graphql.model.resource.Book;
import com.evbx.graphql.test.BaseTest;
import com.evbx.graphql.util.JsonUtil;

import graphql.schema.DataFetchingEnvironment;

import static com.evbx.graphql.test.support.Step.__GIVEN;
import static com.evbx.graphql.test.support.Step.__THEN;
import static com.evbx.graphql.test.support.Step.__WHEN;
import static org.mockito.Mockito.when;

class BookByIdDataFetcherTest extends BaseTest {

    @Autowired
    private BookByIdDataFetcher bookByIdDataFetcher;
    @Mock
    private DataFetchingEnvironment dataFetchingEnvironment;

    private static final String BOOK_JSON_STRING = "{\"id\":100,\"bookName\":\"book-name-0\",\"description\":\"description-0\",\"text\":\"text-0\"}";

    @Test
    void bookByIdDataFetcher() {
        __GIVEN();
        DataFetchingEnvironment dataFetchingEnvironment = Mockito.mock(DataFetchingEnvironment.class);
        when(dataFetchingEnvironment.getArgument("id")).thenReturn("100");
   stubWireMockServer(resourceServiceConfig.getBooksPath() + dataFetchingEnvironment.getArgument("id") , BOOK_JSON_STRING);
        __WHEN();
        Book book = bookByIdDataFetcher.get(dataFetchingEnvironment);
        __THEN();
        Assertions.assertNotNull(book);
        Assertions.assertEquals(book, JsonUtil.fromJson(BOOK_JSON_STRING, Book.class));
    }
}