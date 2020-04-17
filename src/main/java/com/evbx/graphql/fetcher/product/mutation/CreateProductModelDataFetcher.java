package com.evbx.graphql.fetcher.product.mutation;

import org.springframework.stereotype.Component;

import com.evbx.graphql.exception.ApiClientMappingException;
import com.evbx.graphql.fetcher.BaseDataFetcher;
import com.evbx.graphql.model.ProductModelDto;

import graphql.execution.DataFetcherResult;
import graphql.kickstart.execution.error.GenericGraphQLError;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

@Component
public class CreateProductModelDataFetcher extends BaseDataFetcher
        implements DataFetcher<DataFetcherResult<ProductModelDto>> {

    @Override
    public DataFetcherResult<ProductModelDto> get(DataFetchingEnvironment environment) {
        try {
            ProductModelDto productModelDto = productApiClient()
                    .post(productServiceConfig.getProductModelsPath(), getInputMap(environment), ProductModelDto.class);
            return new DataFetcherResult.Builder<ProductModelDto>().data(productModelDto).build();
        } catch (ApiClientMappingException acmEx) {
            return new DataFetcherResult.Builder<ProductModelDto>().error(new GenericGraphQLError(acmEx.getMessage()))
                    .build();
        }
    }
}
