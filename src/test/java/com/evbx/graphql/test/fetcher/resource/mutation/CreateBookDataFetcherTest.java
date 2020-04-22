package com.evbx.graphql.test.fetcher.resource.mutation;

import java.util.HashMap;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.evbx.graphql.fetcher.resource.mutation.CreateBookDataFetcher;
import com.evbx.graphql.model.resource.Book;
import com.evbx.graphql.test.BaseTest;
import com.evbx.graphql.util.JsonUtil;

import static com.evbx.graphql.test.support.Step.__GIVEN;
import static com.evbx.graphql.test.support.Step.__THEN;
import static com.evbx.graphql.test.support.Step.__WHEN;
import static org.mockito.Mockito.when;

class CreateBookDataFetcherTest extends BaseTest {

    @Autowired
    private CreateBookDataFetcher createBookDataFetcher;

    private static final String BOOK_JSON_STRING
            = "{\"bookName\":\"book-name-0\",\"description\":\"description-0\",\"text\":\"text-0\"}";

    @Test
    void createBookDataFetcherTest() {
        __GIVEN();
        when(dataFetchingEnvironment.getArgument("input")).thenReturn(inputMockMap());
        stubWireMockServerForPost(resourceServiceConfig.getBooksPath(), BOOK_JSON_STRING);
        __WHEN();
        Book book = createBookDataFetcher.get(dataFetchingEnvironment).getData();
        __THEN();
        Assertions.assertThat(book).isNotNull();
        Assertions.assertThat(book).isEqualToComparingFieldByField(JsonUtil.fromJson(BOOK_JSON_STRING, Book.class));
    }

    private Map<String, Object> inputMockMap() {
        Map<String, Object> inputMap = new HashMap<>();
        inputMap.put("bookName", "book-name-0");
        inputMap.put("description", "description-0");
        inputMap.put("text", "text-0");
        return inputMap;
    }
}