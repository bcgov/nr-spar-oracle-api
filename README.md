[![Lifecycle:Experimental](https://img.shields.io/badge/Lifecycle-Experimental-339999)](https://github.com/bcgov/nr-spar-oracle-api)
![Coverage](.github/badges/jacoco.svg)
![Branches](.github/badges/branches.svg)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=bcgov_nr-backend-starting-api&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=bcgov_nr-backend-starting-api)

# Natural Resources SPAR Oracle API

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

## Stack

Here you can find a comprehensive list of all languages and tools that are been used
in this service. And also everything you need to get started, build locally, test
and deploy it. 

- Java ecosystem
  - Maven
  - Open JDK 17
  - Spring Web MVC Framework
  - JPA and Hibernate Framework
- Testing
  - JUnit 5
  - Mockito and Mock MVC
  - Automated tests with Postman and Newman
- Database
  - Remote Oracle with secure connection
  - PostgreSQL
- DevOps
  - Docker
  - Docker Composer
  - Sonar Cloud
  - Deploy to OpenShift with GitHub Actions
- Tools (Recommendations)
  - IntelliJ IDEA
  - Postman
  - DBeaver

## Getting started

Once you have cloned this repository, can get it running by typing: `./mvnw spring-boot:run`
from the project root directory. You **must** provide three environment variables for database
access configuration:

- `DATABASE_HOST`
- `DATABASE_PORT`
- `SERVICE_NAME` (the database's name)
- `DATABASE_USER`
- `DATABASE_PASSWORD`

Then head to http://localhost:8090/actuator/health to check if the system was successfully launched:
the `status` property should have the value *UP*.

Before writing your first line of code, and learn more about the checks, including
tests, please take a moment and check out our [CONTRIBUTING](CONTRIBUTING.md) guide.

### Quick look

But if all you want is to take a quick look on the running service, you can do it by
using Docker.

Note that you'll need these environment variables:
```
NR_SPAR_ORACLE_API_VERSION=local
DATABASE_HOST=<host>
DATABASE_PORT=<port>
SERVICE_NAME=<service-name>
DATABASE_USER=<user>
DATABASE_PASSWORD=<pass>
KEYCLOAK_REALM_URL=<realm-server-address>
```

Build the service:
```
docker build -t bcgov/nr-spar-oracle-api:latest .
```

Then run with:
```
docker run -p 8090:8090 \
  -e KEYCLOAK_REALM_URL=https://dev.loginproxy.gov.bc.ca/auth/realms/standard \
  -t bcgov/nr-spar-oracle-api-service:latest
```

However, if you have docker-compose you can do:
```
docker-compose up --build --force-recreate --no-deps
```

You'll need to provide the address of the DNS server to be used by the container in the environment
variable `DNS_ADDRESS`: you can find the address of the DNS server you're using right now using
[nslookup](https://en.wikipedia.org/wiki/Nslookup), for instance.

You can then check the API documentation accessing `localhost:8090/swagger-ui.html`.

## Getting help

As mentioned, we're here to help. Feel free to start a conversation
on Rocket chat or ask a question on Stackoverflow.
