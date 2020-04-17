package com.evbx.graphql.fetcher.resource.mutation;

import org.springframework.stereotype.Component;

import com.evbx.graphql.exception.ApiClientMappingException;
import com.evbx.graphql.fetcher.BaseDataFetcher;
import com.evbx.graphql.model.resource.Specification;

import graphql.execution.DataFetcherResult;
import graphql.kickstart.execution.error.GenericGraphQLError;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

@Component
public class CreateSpecificationDataFetcher extends BaseDataFetcher
        implements DataFetcher<DataFetcherResult<Specification>> {

    @Override
    public DataFetcherResult<Specification> get(DataFetchingEnvironment environment) {
        try {
            Specification specification = resourceApiClient()
                    .post(resourceServiceConfig.getSpecificationsPath(), getInputMap(environment), Specification.class);
            return new DataFetcherResult.Builder<Specification>().data(specification).build();
        } catch (ApiClientMappingException acmEx) {
            return new DataFetcherResult.Builder<Specification>().error(new GenericGraphQLError(acmEx.getMessage()))
                    .build();
        }
    }
}
