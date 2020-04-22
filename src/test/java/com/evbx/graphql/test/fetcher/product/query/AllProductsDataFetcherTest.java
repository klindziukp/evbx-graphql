package com.evbx.graphql.test.fetcher.product.query;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.evbx.graphql.fetcher.product.query.AllProductsDataFetcher;
import com.evbx.graphql.model.ItemData;
import com.evbx.graphql.model.ProductDto;
import com.evbx.graphql.test.BaseTest;
import com.evbx.graphql.util.JsonUtil;
import com.fasterxml.jackson.core.type.TypeReference;

import static com.evbx.graphql.test.support.Step.__GIVEN;
import static com.evbx.graphql.test.support.Step.__THEN;
import static com.evbx.graphql.test.support.Step.__WHEN;

class AllProductsDataFetcherTest extends BaseTest {

    @Autowired
    private AllProductsDataFetcher allProductsDataFetcher;

    private static final String ALL_PRODUCTS_JSON_STRING = "{\"items\":[" +
            "{\"id\":1,\"productName\":\"product-name-1\",\"productModels\":[{\"id\":1,\"modelName\":\"model-name-1\"," +
            "\"descriptions\":[{\"id\":1,\"modelId\":1,\"descriptionLine\":\"desc-line-1\"}]," +
            "\"resources\":{\"e-book\":{\"id\":1,\"bookName\":\"book-name-1\",\"description\":\"book-desc-1\",\"text\":\"book-text-1\"}," +
            "\"industryReport\":{\"id\":1,\"industryName\":\"report-name-1\",\"description\":\"report-desc-1\",\"text\":\"report-text-1\"}," +
            "\"specification\":{\"id\":1,\"specificationName\":\"spec-name-1\",\"description\":\"spec-desc-1\",\"text\":\"spec-text-1\"}}}]}" +
            ",{\"id\":2,\"productName\":\"product-name-2\",\"productModels\":[{\"id\":2,\"modelName\":\"model-name-2\"," +
            "\"descriptions\":[{\"id\":2,\"modelId\":2,\"descriptionLine\":\"desc-line-2\"}]," +
            "\"resources\":{\"e-book\":{\"id\":2,\"bookName\":\"book-name-2\",\"description\":\"book-desc-2\",\"text\":\"book-text-2\"}," +
            "\"industryReport\":{\"id\":2,\"industryName\":\"report-name-2\",\"description\":\"report-desc-2\",\"text\":\"report-text-2\"}," +
            "\"specification\":{\"id\":2,\"specificationName\":\"spec-name-2\",\"description\":\"spec-desc-2\",\"text\":\"spec-text-2\"}}}]}]," +
            "\"total\":2}";

    @Test
    void allProductsDataFetcherTest() {
        __GIVEN();
        stubWireMockServerForGet(productServiceConfig.getProductsPath(), ALL_PRODUCTS_JSON_STRING);
        __WHEN();
        ItemData<ProductDto> data = allProductsDataFetcher.get(dataFetchingEnvironment);
        __THEN();
        Assertions.assertThat(data).isNotNull();
        Assertions.assertThat(data).isEqualToComparingFieldByField(
                JsonUtil.fromJson(ALL_PRODUCTS_JSON_STRING, new TypeReference<ItemData<ProductDto>>() {}));
    }
}