# xkcd-bot

Kotlin implementation of https://gitlab.com/neuware/xkcd-bot for school kotlin TP

The project is a basic gradle project.

## Start the project

### In docker

To start the project with the make file.  
You must first build the image with `make image` then run it with `make ctn-run`.

To make a quick run use `make quick`. It'll do `./gadlew run` in a docker container.

### With gradlew

Simply run `./gradlew run` to run the project.  
You can also use `./gradlew build` to build the project.
The build will be in the `./app/build/distributions/` directory.
