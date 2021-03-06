package com.evbx.graphql.fetcher.product.query;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import com.evbx.graphql.fetcher.BaseDataFetcher;
import com.evbx.graphql.model.Description;
import com.evbx.graphql.model.ItemData;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

@Component
public class AllDescriptionsDataFetcher extends BaseDataFetcher implements DataFetcher<ItemData<Description>> {

    @Override
    public ItemData<Description> get(DataFetchingEnvironment environment) {
        return productApiClient().get(productServiceConfig.getDescriptionsPath(),
                new ParameterizedTypeReference<ItemData<Description>>() {});
    }
}
