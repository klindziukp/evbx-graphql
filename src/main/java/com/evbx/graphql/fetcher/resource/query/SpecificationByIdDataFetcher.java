package com.evbx.graphql.fetcher.resource.query;

import org.springframework.stereotype.Component;

import com.evbx.graphql.fetcher.BaseDataFetcher;
import com.evbx.graphql.model.resource.Specification;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

@Component
public class SpecificationByIdDataFetcher extends BaseDataFetcher implements DataFetcher<Specification> {

    @Override
    public Specification get(DataFetchingEnvironment environment) {
        String path = resourceServiceConfig.getSpecificationsPath() + getId(environment);
        return resourceApiClient().get(path, Specification.class);
    }
}
