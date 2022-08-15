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

### Check-style

We use Google Java Style Guide. If your IDE is IntelliJ, there's a plugin
that can be very helpful. Take a look at
[this page](https://plugins.jetbrains.com/plugin/8527-google-java-format)
or search directly on the IDE Plugins menu.

## Run this application

After setting up your environment you might want to see this service running. 
You can get it up and running by typing `./mvnw spring-boot:run` in the project
root directory.

Note: TODO add here about database and running HOW-TOs.

## Run tests

You can run tests running `./mvnw test`. Tests coverage reposts can be seen
on your commits and pull requests.

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
