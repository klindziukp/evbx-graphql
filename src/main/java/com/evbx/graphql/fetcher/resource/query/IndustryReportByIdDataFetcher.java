package com.evbx.graphql.fetcher.resource.query;

import org.springframework.stereotype.Component;

import com.evbx.graphql.fetcher.BaseDataFetcher;
import com.evbx.graphql.model.resource.IndustryReport;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

@Component
public class IndustryReportByIdDataFetcher extends BaseDataFetcher implements DataFetcher<IndustryReport> {

    @Override
    public IndustryReport get(DataFetchingEnvironment environment) {
        String path = resourceServiceConfig.getIndustryReportPath() + getId(environment);
        return resourceApiClient().get(path, IndustryReport.class);
    }
}
