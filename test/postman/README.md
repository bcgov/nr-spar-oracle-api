# Postman Collection

On this folder there are two files that contains the API tests, all those tests are in the Postman format along with the environment needed to run. The first file `oracle-api.postman_collection.json` it's the collection itself with all the requests and tests, and the second file `oracle-api.postman_environment.json` contains all the variables.

## How to Run

Import the collection and environment to your Postman and use the Collection Runner to run. Make sure to fill in all the values on the environment variables, and if needed ask the dev team. By default, the tests run against the test environment.

## Secrets

All the authentication variables should be marked as the _secret_ type and only set then as _current_, **never fill the default value and also do not commit those values**.
