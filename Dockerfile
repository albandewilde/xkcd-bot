FROM gradle as builder

WORKDIR /usr/src/app/

COPY . .

RUN ./gradlew build
RUN tar -C /bin -xvf app/build/distributions/app.tar

CMD ["/bin/app/bin/./app"]
