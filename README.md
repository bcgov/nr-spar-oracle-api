[![Lifecycle:Experimental](https://img.shields.io/badge/Lifecycle-Experimental-339999)](https://github.com/bcgov/nr-backend-starting-api)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=bcgov_nr-backend-starting-api&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=bcgov_nr-backend-starting-api)

# Natural Resources Back-End Starting API

This repository holds a set of policies, standards, guides, and pipelines to
get started with a back-end API. Before writing your first line of code, please
take a moment and check out our [CONTRIBUTING](CONTRIBUTING.md) guide.

Note: This repository was generated from [greenfield-template](https://github.com/bcgov/greenfield-template)
and in case you're interested in the original README file, we still have it [here](README_template.md).

## Our Policy

- Work in the open: That means that everything we do should be open, should be
public. Please, don't create private repositories unless you have a very strong
reason. Keeping things public is a must follow rule for BC Government.
- Customer centred services: All the work that's been created is to improve users,
customers, and friends usability and experience. Is important to keep that in mind 
because as engineers sometimes we face technical issues, however, our goal is
to have a good product.
- Community based work: Remember that you're not alone. It's very likely that
your problem is someone else's problem. Let's figure it out together. So, ask
a question using our channels. We have [our own Stackoverflow](https://stackoverflow.developer.gov.bc.ca/)
and [our Rocket Chat](https://chat.developer.gov.bc.ca/) channel.

# Stack

Here you can find a comprehensive list of all languages and tools that are been used
in this service. And also everything you need to get started, build locally, test
and deploy it. 

- Java ecosystem
  - Maven
  - Open JDK 17
  - Spring WebFlux Framework with Reactor (reactive programming)
  - JPA and Hibernate Framework
  - JUnit 5, Mockito
- Database
  - PostgreSQL
- DevOps
  - Docker
  - Sonar pipes
  - Deploy to OpenShift cluster
- Tools (Recommendations)
  - IntelliJ IDEA
  - Postman
  - DBeaver

# Getting started

Once you have cloned this repository, you can get it running by typing: `./mvnw spring-boot:run`
from the project root directory. Then head to http://localhost:8080/check you should see a
message and an OK.

To run tests all you need is `./mvnw test`.

Before writing your first line of code, please take a moment and check out
our [CONTRIBUTING](CONTRIBUTING.md) guide.

## Getting help

As mentioned, we're here to help. Feel free to start a conversation
on Rocket chat or ask a question on Stackoverflow.
