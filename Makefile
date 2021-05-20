image:
	docker build --no-cache -t xkcd-bot .

ctn-run:
	docker run -d --env-file ./.env --name xkcd-bot xkcd-bot

ctn-rm: ctn-stop
	@if [ ! -z "`docker container ls -a | grep xkcd-bot`" ]; then \
		docker container rm xkcd-bot; \
		echo "Container removed"; \
	else \
	    echo "Container not present"; \
	fi

ctn-stop:
	@if [ ! -z "`docker container ls | grep xkcd-bot`" ]; then \
		docker container stop xkcd-bot; \
		echo "Container stopped"; \
	else \
	    echo "Container not running"; \
	fi

quick:
	docker run -ti --env-file .env --volume=${PWD}:/home/gradle gradle:7.0.2-jdk11 gradle run
