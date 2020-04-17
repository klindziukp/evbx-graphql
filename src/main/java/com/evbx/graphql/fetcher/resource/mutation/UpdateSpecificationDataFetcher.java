package com.evbx.graphql.fetcher.resource.mutation;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.evbx.graphql.exception.ApiClientMappingException;
import com.evbx.graphql.fetcher.BaseDataFetcher;
import com.evbx.graphql.model.resource.Specification;

import graphql.execution.DataFetcherResult;
import graphql.kickstart.execution.error.GenericGraphQLError;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

@Component
public class UpdateSpecificationDataFetcher extends BaseDataFetcher
        implements DataFetcher<DataFetcherResult<Specification>> {

    @Override
    public DataFetcherResult<Specification> get(DataFetchingEnvironment environment) {
        Map<String, Object> inputMap = getInputMap(environment);
        String path = resourceServiceConfig.getSpecificationsPath() + getIdFromInput(inputMap);
        try {
            Specification specification = resourceApiClient().patch(path, inputMap, Specification.class);
            return new DataFetcherResult.Builder<Specification>().data(specification).build();
        } catch (ApiClientMappingException acmEx) {
            return new DataFetcherResult.Builder<Specification>().error(new GenericGraphQLError(acmEx.getMessage()))
                    .build();
        }
    }
}
