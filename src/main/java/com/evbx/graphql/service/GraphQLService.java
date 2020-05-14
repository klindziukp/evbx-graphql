package com.evbx.graphql.service;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.evbx.graphql.fetcher.product.mutation.CreateDescriptionDataFetcher;
import com.evbx.graphql.fetcher.product.mutation.CreateProductDataFetcher;
import com.evbx.graphql.fetcher.product.mutation.CreateProductModelDataFetcher;
import com.evbx.graphql.fetcher.product.mutation.UpdateDescriptionDataFetcher;
import com.evbx.graphql.fetcher.product.mutation.UpdateProductDataFetcher;
import com.evbx.graphql.fetcher.product.mutation.UpdateProductModelDataFetcher;
import com.evbx.graphql.fetcher.product.query.AllDescriptionsDataFetcher;
import com.evbx.graphql.fetcher.product.query.AllProductModelsDataFetcher;
import com.evbx.graphql.fetcher.product.query.DescriptionByIdDataFetcher;
import com.evbx.graphql.fetcher.product.query.ProductModelByIdDataFetcher;
import com.evbx.graphql.fetcher.resource.mutation.CreateBookDataFetcher;
import com.evbx.graphql.fetcher.resource.mutation.CreateIndustryReportDataFetcher;
import com.evbx.graphql.fetcher.resource.mutation.CreateSpecificationDataFetcher;
import com.evbx.graphql.fetcher.resource.mutation.UpdateBookDataFetcher;
import com.evbx.graphql.fetcher.resource.mutation.UpdateIndustryReportDataFetcher;
import com.evbx.graphql.fetcher.resource.mutation.UpdateSpecificationDataFetcher;
import com.evbx.graphql.fetcher.resource.query.AllBooksDataFetcher;
import com.evbx.graphql.fetcher.resource.query.AllIndustryReportsDataFetcher;
import com.evbx.graphql.fetcher.product.query.AllProductsDataFetcher;
import com.evbx.graphql.fetcher.resource.query.AllSpecificationsDataFetcher;
import com.evbx.graphql.fetcher.product.query.ProductByIdDataFetcher;
import com.evbx.graphql.fetcher.resource.query.BookByIdDataFetcher;
import com.evbx.graphql.fetcher.resource.query.IndustryReportByIdDataFetcher;
import com.evbx.graphql.fetcher.resource.query.SpecificationByIdDataFetcher;

import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;

@Service
public class GraphQLService {

    @Value("classpath:schema.graphql")
    private Resource resource;

    // 'Product' service related 'query' data fetchers
    @Autowired private AllProductsDataFetcher allProductsDataFetcher;
    @Autowired private ProductByIdDataFetcher productByIdDataFetcher;
    @Autowired private AllProductModelsDataFetcher allProductModelsDataFetcher;
    @Autowired private ProductModelByIdDataFetcher productModelByIdDataFetcher;
    @Autowired private AllDescriptionsDataFetcher allDescriptionsDataFetcher;
    @Autowired private DescriptionByIdDataFetcher descriptionByIdDataFetcher;
    // 'Product' service related 'mutation' data fetchers
    @Autowired private CreateProductDataFetcher createProductDataFetcher;
    @Autowired private UpdateProductDataFetcher updateProductDataFetcher;
    @Autowired private CreateProductModelDataFetcher createProductModelDataFetcher;
    @Autowired private UpdateProductModelDataFetcher updateProductModelDataFetcher;
    @Autowired private CreateDescriptionDataFetcher createDescriptionDataFetcher;
    @Autowired private UpdateDescriptionDataFetcher updateDescriptionDataFetcher;
    // 'Resource' service related 'query' data fetchers
    @Autowired private AllBooksDataFetcher allBooksDataFetcher;
    @Autowired private AllIndustryReportsDataFetcher allIndustryReportsDataFetcher;
    @Autowired private AllSpecificationsDataFetcher allSpecificationsDataFetcher;
    @Autowired private BookByIdDataFetcher bookByIdDataFetcher;
    @Autowired private IndustryReportByIdDataFetcher industryReportByIdDataFetcher;
    @Autowired private SpecificationByIdDataFetcher specificationByIdDataFetcher;
    // 'Resource' service related 'mutation' data fetchers
    @Autowired private CreateBookDataFetcher createBookDataFetcher;
    @Autowired private UpdateBookDataFetcher updateBookDataFetcher;
    @Autowired private CreateIndustryReportDataFetcher createIndustryReportDataFetcher;
    @Autowired private UpdateIndustryReportDataFetcher updateIndustryReportDataFetcher;
    @Autowired private CreateSpecificationDataFetcher createSpecificationDataFetcher;
    @Autowired private UpdateSpecificationDataFetcher updateSpecificationDataFetcher;

    @Bean
    public GraphQLSchema schema() throws IOException {
        InputStream inputStream = resource.getInputStream();
        TypeDefinitionRegistry tr = new SchemaParser().parse(inputStream);
        RuntimeWiring rw = buildRuntimeWiring();
        return new SchemaGenerator().makeExecutableSchema(tr, rw);
    }

    private RuntimeWiring buildRuntimeWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type("Query", typeWiring -> typeWiring
                        // Product
                        .dataFetcher("allProducts", allProductsDataFetcher)
                        .dataFetcher("productById", productByIdDataFetcher)
                        .dataFetcher("allProductModels", allProductModelsDataFetcher)
                        .dataFetcher("productModelById", productModelByIdDataFetcher)
                        .dataFetcher("allDescriptions", allDescriptionsDataFetcher)
                        .dataFetcher("descriptionById", descriptionByIdDataFetcher)
                        // Resource
                        .dataFetcher("allBooks", allBooksDataFetcher)
                        .dataFetcher("allIndustryReports", allIndustryReportsDataFetcher)
                        .dataFetcher("allSpecifications", allSpecificationsDataFetcher)
                        .dataFetcher("bookById", bookByIdDataFetcher)
                        .dataFetcher("industryReportById", industryReportByIdDataFetcher)
                        .dataFetcher("specificationById", specificationByIdDataFetcher))
                .type("Mutation", typeWiring -> typeWiring
                        // Product
                        .dataFetcher("createProduct", createProductDataFetcher)
                        .dataFetcher("updateProduct", updateProductDataFetcher)
                        .dataFetcher("createProductModel", createProductModelDataFetcher)
                        .dataFetcher("updateProductModel", updateProductModelDataFetcher)
                        .dataFetcher("createDescription", createDescriptionDataFetcher)
                        .dataFetcher("updateDescription", updateDescriptionDataFetcher)
                        // Resource
                        .dataFetcher("createBook", createBookDataFetcher)
                        .dataFetcher("updateBook", updateBookDataFetcher)
                        .dataFetcher("createIndustryReport", createIndustryReportDataFetcher)
                        .dataFetcher("updateIndustryReport", updateIndustryReportDataFetcher)
                        .dataFetcher("createSpecification", createSpecificationDataFetcher)
                        .dataFetcher("updateSpecification", updateSpecificationDataFetcher))
                .build();
    }
}
