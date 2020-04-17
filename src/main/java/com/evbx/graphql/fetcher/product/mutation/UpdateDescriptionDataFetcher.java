package com.evbx.graphql.fetcher.product.mutation;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.evbx.graphql.exception.ApiClientMappingException;
import com.evbx.graphql.fetcher.BaseDataFetcher;
import com.evbx.graphql.model.Description;

import graphql.execution.DataFetcherResult;
import graphql.kickstart.execution.error.GenericGraphQLError;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

@Component
public class UpdateDescriptionDataFetcher extends BaseDataFetcher
        implements DataFetcher<DataFetcherResult<Description>> {

    @Override
    public DataFetcherResult<Description> get(DataFetchingEnvironment environment) {
        Map<String, Object> inputMap = getInputMap(environment);
        String path = productServiceConfig.getDescriptionsPath() + getIdFromInput(inputMap);
        try {
            Description description = productApiClient().patch(path, inputMap, Description.class);
            return new DataFetcherResult.Builder<Description>().data(description).build();
        } catch (ApiClientMappingException acmEx) {
            return new DataFetcherResult.Builder<Description>().error(new GenericGraphQLError(acmEx.getMessage()))
                    .build();
        }
    }
}
