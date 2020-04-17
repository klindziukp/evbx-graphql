package com.evbx.graphql.fetcher.product.mutation;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.evbx.graphql.exception.ApiClientMappingException;
import com.evbx.graphql.fetcher.BaseDataFetcher;
import com.evbx.graphql.model.ProductDto;

import graphql.execution.DataFetcherResult;
import graphql.kickstart.execution.error.GenericGraphQLError;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

@Component
public class UpdateProductDataFetcher extends BaseDataFetcher implements DataFetcher<DataFetcherResult<ProductDto>> {

    @Override
    public DataFetcherResult<ProductDto> get(DataFetchingEnvironment environment) {
        Map<String, Object> inputMap = getInputMap(environment);
        String path = productServiceConfig.getProductsPath() + getIdFromInput(inputMap);
        try {
            ProductDto productDto = productApiClient().patch(path, inputMap, ProductDto.class);
            return new DataFetcherResult.Builder<ProductDto>().data(productDto).build();
        } catch (ApiClientMappingException acmEx) {
            return new DataFetcherResult.Builder<ProductDto>().error(new GenericGraphQLError(acmEx.getMessage()))
                    .build();
        }
    }
}
