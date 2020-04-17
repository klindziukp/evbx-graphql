package com.evbx.graphql.fetcher.product.query;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import com.evbx.graphql.fetcher.BaseDataFetcher;
import com.evbx.graphql.model.ItemData;
import com.evbx.graphql.model.ProductDto;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

@Component
public class AllProductsDataFetcher extends BaseDataFetcher implements DataFetcher<ItemData<ProductDto>> {

    @Override
    public ItemData<ProductDto> get(DataFetchingEnvironment environment) {
        return productApiClient()
                .get(productServiceConfig.getProductsPath(), new ParameterizedTypeReference<ItemData<ProductDto>>() {});
    }
}
