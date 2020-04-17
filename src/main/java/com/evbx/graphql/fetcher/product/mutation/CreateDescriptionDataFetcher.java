package com.evbx.graphql.fetcher.product.mutation;

import org.springframework.stereotype.Component;

import com.evbx.graphql.exception.ApiClientMappingException;
import com.evbx.graphql.fetcher.BaseDataFetcher;
import com.evbx.graphql.model.Description;

import graphql.execution.DataFetcherResult;
import graphql.kickstart.execution.error.GenericGraphQLError;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

@Component
public class CreateDescriptionDataFetcher extends BaseDataFetcher
        implements DataFetcher<DataFetcherResult<Description>> {

    @Override
    public DataFetcherResult<Description> get(DataFetchingEnvironment environment) {
        try {
            Description description = productApiClient()
                    .post(productServiceConfig.getDescriptionsPath(), getInputMap(environment), Description.class);
            return new DataFetcherResult.Builder<Description>().data(description).build();
        } catch (ApiClientMappingException acmEx) {
            return new DataFetcherResult.Builder<Description>().error(new GenericGraphQLError(acmEx.getMessage()))
                    .build();
        }
    }
}
