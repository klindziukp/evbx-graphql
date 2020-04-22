package com.evbx.graphql.test.fetcher.resource.mutation;

import java.util.HashMap;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import com.evbx.graphql.fetcher.resource.mutation.UpdateBookDataFetcher;
import com.evbx.graphql.model.resource.Book;
import com.evbx.graphql.test.BaseTest;
import com.evbx.graphql.util.JsonUtil;

import static com.evbx.graphql.test.support.Step.__GIVEN;
import static com.evbx.graphql.test.support.Step.__THEN;
import static com.evbx.graphql.test.support.Step.__WHEN;
import static org.mockito.Mockito.when;

class UpdateBookDataFetcherTest extends BaseTest {

    @Autowired
    private UpdateBookDataFetcher updateBookDataFetcher;

    private static final String BOOK_JSON_STRING
            = "{\"id\":100,\"bookName\":\"book-name-X\",\"description\":\"description-X\",\"text\":\"text-X\"}";

    @Test
    void updateBookDataFetcherTest() {
        __GIVEN();
        when(dataFetchingEnvironment.getArgument("input")).thenReturn(inputMockMap());
        stubWireMockServerForPatch(resourceServiceConfig.getBooksPath() + inputMockMap().get("id"), BOOK_JSON_STRING);
        __WHEN();
        Book book = updateBookDataFetcher.get(dataFetchingEnvironment).getData();
        __THEN();
        Assertions.assertThat(book).isNotNull();
        Assertions.assertThat(book).isEqualToComparingFieldByField(JsonUtil.fromJson(BOOK_JSON_STRING, Book.class));
    }

    private Map<String, Object> inputMockMap() {
        Map<String, Object> inputMap = new HashMap<>();
        inputMap.put("id", 100L);
        inputMap.put("bookName", "book-name-X");
        inputMap.put("description", "description-X");
        inputMap.put("text", "text-X");
        return inputMap;
    }
}