package com.evbx.graphql.fetcher.product.query;

import org.springframework.stereotype.Component;

import com.evbx.graphql.fetcher.BaseDataFetcher;
import com.evbx.graphql.model.ProductDto;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

@Component
public class ProductByIdDataFetcher extends BaseDataFetcher implements DataFetcher<ProductDto> {

    @Override
    public ProductDto get(DataFetchingEnvironment environment) {
        String path = productServiceConfig.getProductsPath() + getId(environment);
        return productApiClient().get(path, ProductDto.class);
    }
}

