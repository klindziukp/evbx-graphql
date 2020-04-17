package com.evbx.graphql.fetcher.resource.mutation;

import org.springframework.stereotype.Component;

import com.evbx.graphql.exception.ApiClientMappingException;
import com.evbx.graphql.fetcher.BaseDataFetcher;
import com.evbx.graphql.model.resource.IndustryReport;

import graphql.execution.DataFetcherResult;
import graphql.kickstart.execution.error.GenericGraphQLError;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

@Component
public class CreateIndustryReportDataFetcher extends BaseDataFetcher
        implements DataFetcher<DataFetcherResult<IndustryReport>> {

    @Override
    public DataFetcherResult<IndustryReport> get(DataFetchingEnvironment environment) {
        try {
            IndustryReport industryReport = resourceApiClient()
                    .post(resourceServiceConfig.getIndustryReportPath(), getInputMap(environment),
                            IndustryReport.class);
            return new DataFetcherResult.Builder<IndustryReport>().data(industryReport).build();
        } catch (ApiClientMappingException acmEx) {
            return new DataFetcherResult.Builder<IndustryReport>().error(new GenericGraphQLError(acmEx.getMessage()))
                    .build();
        }
    }
}
