package com.evbx.graphql.fetcher.resource.query;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import com.evbx.graphql.fetcher.BaseDataFetcher;
import com.evbx.graphql.model.ItemData;
import com.evbx.graphql.model.resource.Book;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

@Component
public class AllBooksDataFetcher extends BaseDataFetcher implements DataFetcher<ItemData<Book>> {

    @Override
    public ItemData<Book> get(DataFetchingEnvironment environment) {
        return resourceApiClient()
                .get(resourceServiceConfig.getBooksPath(), new ParameterizedTypeReference<ItemData<Book>>() {});
    }
}
