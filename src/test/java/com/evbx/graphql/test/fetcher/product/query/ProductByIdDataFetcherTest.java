package com.evbx.graphql.test.fetcher.product.query;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.evbx.graphql.fetcher.product.query.ProductByIdDataFetcher;
import com.evbx.graphql.model.ProductDto;
import com.evbx.graphql.test.BaseTest;
import com.evbx.graphql.util.JsonUtil;

import static com.evbx.graphql.test.support.Step.__GIVEN;
import static com.evbx.graphql.test.support.Step.__THEN;
import static com.evbx.graphql.test.support.Step.__WHEN;
import static org.mockito.Mockito.when;

class ProductByIdDataFetcherTest extends BaseTest {

    @Autowired
    private ProductByIdDataFetcher productByIdDataFetcher;

    private static final String PRODUCT_JSON_STRING =
            "{\"id\":103,\"productName\":\"product-name-1\",\"productModels\":[{\"id\":1,\"modelName\":\"model-name-1\"," +
                    "\"descriptions\":[{\"id\":1,\"modelId\":1,\"descriptionLine\":\"desc-line-1\"}]," +
                    "\"resources\":{\"e-book\":{\"id\":1,\"bookName\":\"book-name-1\",\"description\":\"book-desc-1\",\"text\":\"book-text-1\"}," +
                    "\"industryReport\":{\"id\":1,\"industryName\":\"report-name-1\",\"description\":\"report-desc-1\",\"text\":\"report-text-1\"}," +
                    "\"specification\":{\"id\":1,\"specificationName\":\"spec-name-1\",\"description\":\"spec-desc-1\",\"text\":\"spec-text-1\"}}}]}";

    @Test
    void productByIdDataFetcherTest() {
        __GIVEN();
        when(dataFetchingEnvironment.getArgument("id")).thenReturn("103");
        stubWireMockServerForGet(productServiceConfig.getProductsPath() + dataFetchingEnvironment.getArgument("id"),
                PRODUCT_JSON_STRING);
        __WHEN();
        ProductDto productDto = productByIdDataFetcher.get(dataFetchingEnvironment);
        __THEN();
        Assertions.assertThat(productDto).isNotNull();
        Assertions.assertThat(productDto)
                .isEqualToComparingFieldByField(JsonUtil.fromJson(PRODUCT_JSON_STRING, ProductDto.class));
    }
}