package com.evbx.graphql.fetcher.resource.mutation;

import org.springframework.stereotype.Component;

import com.evbx.graphql.exception.ApiClientMappingException;
import com.evbx.graphql.fetcher.BaseDataFetcher;
import com.evbx.graphql.model.resource.Book;

import graphql.execution.DataFetcherResult;
import graphql.kickstart.execution.error.GenericGraphQLError;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

@Component
public class CreateBookDataFetcher extends BaseDataFetcher implements DataFetcher<DataFetcherResult<Book>> {

    @Override
    public DataFetcherResult<Book> get(DataFetchingEnvironment environment) {
        try {
            Book book = resourceApiClient()
                    .post(resourceServiceConfig.getBooksPath(), getInputMap(environment), Book.class);
            return new DataFetcherResult.Builder<Book>().data(book).build();
        } catch (ApiClientMappingException acmEx) {
            return new DataFetcherResult.Builder<Book>().error(new GenericGraphQLError(acmEx.getMessage())).build();
        }
    }
}
