package com.evbx.graphql.fetcher.product.query;

import org.springframework.stereotype.Component;

import com.evbx.graphql.fetcher.BaseDataFetcher;
import com.evbx.graphql.model.ProductModelDto;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

@Component
public class ProductModelByIdDataFetcher extends BaseDataFetcher implements DataFetcher<ProductModelDto> {

    @Override
    public ProductModelDto get(DataFetchingEnvironment environment) {
        String path = productServiceConfig.getProductModelsPath() + getId(environment);
        return productApiClient().get(path, ProductModelDto.class);
    }
}

