# xkcd-bot

Kotlin implementation of https://gitlab.com/neuware/xkcd-bot for school kotlin TP.  
(The search by key word or regex isn't implemented)

The project is a basic gradle project.

## Start the project

### In docker

Before all you must copy the `.env.tpl` file into `.env` and complete it with
the token of your bot given by discord.

To start the project with the make file.  
You must first build the image with `make image` then run it with `make ctn-run`.

To make a quick run use `make quick`. It'll do `gadle run` in a docker container.

### With gradlew

Simply run `./gradlew run` to run the project.  
You can also use `./gradlew build` to build the project.
The build will be in the `./app/build/distributions/` directory.

## TP notation

Out professor tell us the code should be clean and idiomatic kotlin.

It should also use all of this:

- [x] - lazy
- [x] - latinit
- [ ] - HOF
- [x] - data class
- [ ] - delegation
- [x] - if
- [x] - for loop
- [ ] - smart cast (is)
- [x] - collection
- [ ] - extentions
- [x] - lambda
- [x] - koroutine
- [x] - scope func
