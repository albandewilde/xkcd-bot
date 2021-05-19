FROM gradle:7.0.2 as builder

WORKDIR /usr/src/app/

COPY . .

# Don't use gradle warpper to don't update gradle
# on each image build
RUN gradle --console=rich build
RUN tar -C /bin -xvf app/build/distributions/app.tar

CMD ["/bin/app/bin/./app"]
