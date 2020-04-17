package com.evbx.graphql.fetcher;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.evbx.graphql.apiclient.ApiClient;
import com.evbx.graphql.config.ProductServiceConfig;
import com.evbx.graphql.config.ResourceServiceConfig;
import com.evbx.graphql.util.CastUtil;

import graphql.schema.DataFetchingEnvironment;

@Component
public class BaseDataFetcher {

    @Autowired protected ResourceServiceConfig resourceServiceConfig;
    @Autowired protected ProductServiceConfig productServiceConfig;

    protected ApiClient productApiClient() {
        return new ApiClient(productServiceConfig);
    }

    protected ApiClient resourceApiClient() {
        return new ApiClient(resourceServiceConfig);
    }

    protected Map<String, Object> getInputMap(DataFetchingEnvironment environment) {
        return environment.getArgument("input");
    }

    protected String getIdFromInput(Map<String, Object> getInputMap) {
        return CastUtil.toString(getInputMap.get("id"));
    }

    protected String getId(DataFetchingEnvironment environment) {
        return CastUtil.toString(environment.getArgument("id"));
    }
}
