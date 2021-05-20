FROM gradle:7.0.2-jdk11 as builder

WORKDIR /usr/src/app/

COPY . .

RUN gradle build


FROM openjdk:11-slim

WORKDIR /bin/xkcd-bot/

COPY --from=builder /usr/src/app/app/build/distributions/app.tar .
RUN tar -C /bin -xvf/bin/xkcd-bot/app.tar

CMD ["/bin/app/bin/./app"]
