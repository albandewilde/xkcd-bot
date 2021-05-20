image:
	docker build --no-cache -t xkcd-bot .
ctn-run:
	docker run -d --env-file ./.env --name xkcd-bot xkcd-bot
ctn-rm:
	@if [ ! -z "`docker container ls -a | grep xkcd-bot`" ]; then \
		docker container rm xkcd-bot; \
		echo "Container removed"; \
	else \
	    echo "Container not present"; \
	fi
quick:
	docker run -ti --volume=${PWD}:/home/gradle gradle ./gradlew run
