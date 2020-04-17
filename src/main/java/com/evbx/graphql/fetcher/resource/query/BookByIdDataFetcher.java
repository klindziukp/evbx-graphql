package com.evbx.graphql.fetcher.resource.query;

import org.springframework.stereotype.Component;

import com.evbx.graphql.fetcher.BaseDataFetcher;
import com.evbx.graphql.model.resource.Book;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

@Component
public class BookByIdDataFetcher extends BaseDataFetcher implements DataFetcher<Book> {

    @Override
    public Book get(DataFetchingEnvironment environment) {
        String path = resourceServiceConfig.getBooksPath() + getId(environment);
        return resourceApiClient().get(path, Book.class);
    }
}
