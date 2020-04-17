package com.evbx.graphql.fetcher.product.query;

import org.springframework.stereotype.Component;

import com.evbx.graphql.fetcher.BaseDataFetcher;
import com.evbx.graphql.model.Description;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

@Component
public class DescriptionByIdDataFetcher extends BaseDataFetcher implements DataFetcher<Description> {

    @Override
    public Description get(DataFetchingEnvironment environment) {
        String path = productServiceConfig.getDescriptionsPath() + getId(environment);
        return productApiClient().get(path, Description.class);
    }
}

