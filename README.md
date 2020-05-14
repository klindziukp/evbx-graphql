[![Build Status](https://travis-ci.org/klindziukp/evbx-graphql.svg?branch=master)](https://travis-ci.org/github/klindziukp/evbx-graphql)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=klindziukp_evbx-graphql&metric=alert_status)](https://sonarcloud.io/dashboard?id=klindziukp_evbx-graphql)
[![Code Coverage](https://sonarcloud.io/api/project_badges/measure?project=klindziukp_evbx-graphql&metric=coverage)](https://sonarcloud.io/component_measures?id=klindziukp_evbx-graphql&metric=coverage)

# evbx-graphql server
GraphQL service for the Evbx project to retrieve data from '[evbx-product](https://github.com/klindziukp/evbx-product)' and '[evbx-resource](https://github.com/klindziukp/evbx-resource)' servers 
### What is GraphQL
GraphQL is a query language (that’s what the “QL” stands for) for APIs and a runtime for fulfilling those queries with your existing data. Basically, it is used to load data from a server to a client - it’s a way to get data from an API into your application. And as you’ll see, it does this in a much more efficient manner than traditional methods and services.
### GraphQL vs REST
REST API where you typically have a specific endpoint or resource you’re hitting that determines an entire block of data that comes back in the returning JSON response, which then needs to be parsed and scattered.
GraphQL is instead established around schema, queries, and resolvers and rather aims to improve upon the REST philosophy by allowing you to ask for a specific piece of data - not just the entire block. No need for parsing through a long stream of data - you only get what you ask for. And what you ask for could be compiled from several different REST APIs.
### Queries and Mutations
[Queries](https://graphql.org/learn/queries/) definitions are named, and include inputs (optional) and outputs. Callers that execute queries provide any necessary inputs and specify which outputs and fields are to be included in the response.
```
{
  allBooks {
    items {
      id
      bookName
      description
      text
    }
    total
  }
}
```

[Mutations](https://graphql.org/learn/queries/#mutations) definitions provided a named method for mutating (creating or modifying) data on the back-end. Just like queries, mutations are named, may include inputs and outputs.
```
mutation
 {
  createDescription(
  input:
  { modelId: 100
    descriptionLine: "description"
      }
    )
  {
    modelId
    descriptionLine
  }
}
```
## Application configuration
* Set up [evbx-resource](https://github.com/klindziukp/evbx-resource) server using [resource-instructions](https://github.com/klindziukp/evbx-resource/blob/master/README.md)
* Set up [evbx-product](https://github.com/klindziukp/evbx-product) server using [product-instructions](https://github.com/klindziukp/evbx-product/blob/master/README.md)
* Make sure that `evbx-product` and `evbx-resources` services are running.
* Configure `ebvx-product` and `ebvx-resources` servers endpoint in `src/main/resources/application.yml`
```
services:
  product:
    baseUrl: http://localhost:8001        ## or your custom url for 'evbx-product' server
    username: admin                       ## or your custom username for 'evbx-product' server
    password: admin                       ## or your custom password for 'evbx-product' server
    ...
    ..
    . 
  resource:
    baseUrl: http://localhost:8001        ## or your custom url for 'evbx-resource' server
    username: admin                       ## or your custom username for 'evbx-resource' server
    password: admin                       ## or your custom password for 'evbx-resource' server
    ...
    ..
    .
``` 
## Application start
* Execute command from __project root__ directory `./gradlew clean build bootRun`

## GraphQL client
 [GraphQL Playground](https://github.com/prisma-labs/graphql-playground) GraphQL IDE for better development. 
 GraphQL Playground is a  powerful GraphQL IDE enabling better (local) development work flows.
 GraphQL Playground ships with the following additional features: 
 - Interactive, multi-column schema documentation
 - Automatic schema reloading
 - Support for GraphQL Subscriptions
 - Query history
 - Configuration of HTTP headers
 - Tabs
