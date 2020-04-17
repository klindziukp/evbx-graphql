package com.evbx.graphql.fetcher.product.query;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import com.evbx.graphql.fetcher.BaseDataFetcher;
import com.evbx.graphql.model.ItemData;
import com.evbx.graphql.model.ProductModelDto;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

@Component
public class AllProductModelsDataFetcher extends BaseDataFetcher implements DataFetcher<ItemData<ProductModelDto>> {

    @Override
    public ItemData<ProductModelDto> get(DataFetchingEnvironment environment) {
        return productApiClient().get(productServiceConfig.getProductModelsPath(),
                new ParameterizedTypeReference<ItemData<ProductModelDto>>() {});
    }
}
