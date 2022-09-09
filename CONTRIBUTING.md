# Contributing guide

Thanks for taking a moment and reading this guide. Is very important to have 
everyone on the same page. This guide describes how to:
- Set up your environment
- Run this application
- Run tests
- Submit pull requests
- Follow our code practices

(If you are new to GitHub, you might start with a [basic tutorial](https://help.github.com/articles/set-up-git) and check out a more detailed guide to [pull requests](https://help.github.com/articles/using-pull-requests/).)

All contributors retain the original copyright to their stuff, but by
contributing to this project, you grant a world-wide, royalty-free, 
perpetual, irrevocable, non-exclusive, transferable license to all 
users **under the terms of the [license](./LICENSE.md) under which 
this project is distributed**.

## Set up your environment

### Git

Make sure you have Git installed on your machine. You can follow
[this link](https://git-scm.com/downloads) for instructions.

### Docker

We containerize our application with Docker images. 

Note: things are way 
easier if you don't need to run docker commands with sudo. Take a look
[here](https://docs.docker.com/engine/install/#server) to learn how to
install. Note that Docker Desktop shouldn't be used for this project,
due to license matter.

### Java and Maven

An easy way of getting both Java and Maven on your machine is using 
SDK Man. Take a look [here](https://sdkman.io/) to learn how to install.
For this project we're using Java 17.

### IDE

We recommend IntelliJ IDEA Community, because all of its plugins and
configurations possibilities, here's [the website](https://www.jetbrains.com/idea/download).
But feel free to use Eclipse or other IDE of your choice.

### Code style

Our Java code is formatted following the [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html).
A formatter and plugins based on it for Eclipse and IntelliJ are available and  make writing
style-conformant code quite easy. Check the installation notes on the
[formatter's project page](https://github.com/google/google-java-format).

We configured a tool to validate changes submitted to us in accordance to our style guide. **Passing
such validation, however, doesn't mean that the code conforms to the style guide**, as some rules
cannot be checked by this tool. We ask you to check if your code adheres to the following rules
before submitting it.

- [2.2 File encoding: UTF-8](https://google.github.io/styleguide/javaguide.html#s2.2-file-encoding)
- [5.2.2 Class names](https://google.github.io/styleguide/javaguide.html#s5.2.2-class-names)
- [5.2.3 Method names](https://google.github.io/styleguide/javaguide.html#s5.2.3-method-names)
- [5.2.4 Constant names](https://google.github.io/styleguide/javaguide.html#s5.2.4-constant-names)
- [5.3 Camel case: defined](https://google.github.io/styleguide/javaguide.html#s5.3-camel-case)
- [6.1 @Override: always used](https://google.github.io/styleguide/javaguide.html#s6.1-override-annotation)

You can check your code before submitting with `./mvnw --no-transfer-progress checkstyle:checkstyle -Dcheckstyle.skip=false --file pom.xml`

## Run this application

After setting up your environment you might want to see this service running. 
You can get it up and running by typing `./mvnw spring-boot:run` in the project
root directory.

In case you want to debug with remote JVM, you can do it with this command:
`./mvnw spring-boot:run -Dspring-boot.run.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005"`

Note: TODO add here about database and running HOW-TOs.

## Run tests

For unit tests, please use this command: `./mvnw test --file pom.xml`

And for integration tests, this one: `./mvnw verify -P integration-test --file pom.xml`

Tests coverage reports can be seen on your commits and pull requests. But in case you 
want to check locally, use this command to run all tests `./mvnw --no-transfer-progress clean verify -P all-tests --file pom.xml`,
and check out the files inside `target/coverage-reports/`

## Submit pull requests

We use git flow, so all code changes happen through Pull Requests. There's a
Pull Request template that you can fill. The more complete the better. If you
have images, screen capture or diagrams, that helps a lot. Don't forget to add
reviewers, assign to yourself and add a label.

## Follow our best practices

- Java source code must be formatted according to
[Google Java Style Guide](https://google.github.io/styleguide/javaguide.html),
as mentioned. There's a pipeline to unsure all of our code is good to go.
- We try to use [conventional commits](https://www.conventionalcommits.org/)
because it makes the process of generating changelogs way easier. So we encourage
you to read at least the [summary](https://www.conventionalcommits.org/en/v1.0.0/#summary)
that summarize and give some examples.
