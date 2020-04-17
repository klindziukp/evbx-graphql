package com.evbx.graphql.fetcher.resource.query;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import com.evbx.graphql.fetcher.BaseDataFetcher;
import com.evbx.graphql.model.ItemData;
import com.evbx.graphql.model.resource.Specification;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

@Component
public class AllSpecificationsDataFetcher extends BaseDataFetcher implements DataFetcher<ItemData<Specification>> {

    @Override
    public ItemData<Specification> get(DataFetchingEnvironment environment) {
        return resourceApiClient().get(resourceServiceConfig.getSpecificationsPath(),
                new ParameterizedTypeReference<ItemData<Specification>>() {});
    }
}
