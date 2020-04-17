package com.evbx.graphql.fetcher.product.mutation;

import org.springframework.stereotype.Component;

import com.evbx.graphql.exception.ApiClientMappingException;
import com.evbx.graphql.fetcher.BaseDataFetcher;
import com.evbx.graphql.model.ProductDto;

import graphql.execution.DataFetcherResult;
import graphql.kickstart.execution.error.GenericGraphQLError;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

@Component
public class CreateProductDataFetcher extends BaseDataFetcher implements DataFetcher<DataFetcherResult<ProductDto>> {

    @Override
    public DataFetcherResult<ProductDto> get(DataFetchingEnvironment environment) {
        try {
            ProductDto productDto = productApiClient()
                    .post(productServiceConfig.getProductsPath(), getInputMap(environment), ProductDto.class);
            return new DataFetcherResult.Builder<ProductDto>().data(productDto).build();
        } catch (ApiClientMappingException acmEx) {
            return new DataFetcherResult.Builder<ProductDto>().error(new GenericGraphQLError(acmEx.getMessage()))
                    .build();
        }
    }
}
