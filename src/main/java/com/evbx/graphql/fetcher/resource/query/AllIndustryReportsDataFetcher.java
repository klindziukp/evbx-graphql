package com.evbx.graphql.fetcher.resource.query;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import com.evbx.graphql.fetcher.BaseDataFetcher;
import com.evbx.graphql.model.ItemData;
import com.evbx.graphql.model.resource.IndustryReport;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

@Component
public class AllIndustryReportsDataFetcher extends BaseDataFetcher implements DataFetcher<ItemData<IndustryReport>> {

    @Override
    public ItemData<IndustryReport> get(DataFetchingEnvironment environment) {
        return resourceApiClient().get(resourceServiceConfig.getIndustryReportPath(),
                new ParameterizedTypeReference<ItemData<IndustryReport>>() {});
    }
}
