# Contributing guide

Thanks for taking a moment and reading this guide. Is very important to have 
everyone on the same page. This guide describes how to:
- Get your environment ready
- Follow our code practices
- Run tests
- Submitting PRs

(If you are new to GitHub, you might start with a [basic tutorial](https://help.github.com/articles/set-up-git) and check out a more detailed guide to [pull requests](https://help.github.com/articles/using-pull-requests/).)

All contributors retain the original copyright to their stuff, but by 
contributing to this project, you grant a world-wide, royalty-free, 
perpetual, irrevocable, non-exclusive, transferable license to all 
users **under the terms of the [license](./LICENSE.md) under which 
this project is distributed**.

## Get your environment ready

### Git

Make sure you have git installed on your machine. You can follow
[this link](https://git-scm.com/downloads) for instructions.

### Docker

We have our environment build over Docker images. Note: things are way 
easier if you don't need to run docker commands with sudo. Take a look
[here](https://docs.docker.com/engine/install/) to learn how to install

### Java and Maven

An easy way of getting both Java and Maven on your machine is using 
SDK Man. Take a look [here](https://sdkman.io/) to learn how to install.

### IDE

We recommend IntelliJ IDEA Community, because all of its plugins and
configurations possibilities, here's [the website](https://www.jetbrains.com/idea/download).
But feel free to use Eclipse or other IDE of your choice.

## Follow our best practices

- Java code must be formatted according to
[Google Java Style Guide](https://google.github.io/styleguide/javaguide.html),
if you're using IntelliJ IDEA you can set it up by downloading the plugin
at [this page](https://plugins.jetbrains.com/plugin/8527-google-java-format),
maintained by Google.
- Before submitting your commit, make sure your code

## Run tests

You can run tests running `make tests`

## Submitting PRs

- We use git flow, so all code changes happen through Pull Requests. There's a
Pull Request template that you can fill. The most complete the better. If you
have images, prints or diagrams, that helps a lot. Don't forget to add 
reviewers, assign to yourself and add a label.
