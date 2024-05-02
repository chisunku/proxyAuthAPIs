FROM java:8-jdk-alpine

COPY ./target/proxyAuth-0.0.1-SNAPSHOT.jar /usr/app/

WORKDIR /usr/app

RUN sh -c 'touch proxyAuth-0.0.1-SNAPSHOT.jar'

ENTRYPOINT ["java","
-jar","proxyAuth-0.0.1-SNAPSHOT.jar"]