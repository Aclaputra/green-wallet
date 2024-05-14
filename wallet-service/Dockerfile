FROM maven:3.8.8 AS build

ARG APP_PORT=8081
ARG DB_HOST=db
ARG DB_PORT=5432
ARG DB_NAME=kong
ARG DB_USER=kong
ARG DB_PASSWORD=kong

WORKDIR /app

COPY pom.xml .

COPY src ./src

RUN mvn clean package -DskipTests \
  -DAPP_PORT=${APP_PORT} \
  -DDB_HOST=${DB_HOST} \
  -DDB_PORT=${DB_PORT} \
  -DDB_NAME=${DB_NAME} \
  -DDB_USER=${DB_USER} \
  -DDB_PASSWORD=${DB_PASSWORD}

FROM openjdk:17-jdk-alpine

LABEL key="0.0.1-SNAPSHOT"

VOLUME /tmp

COPY --from=build /app/target/green.wallet-0.0.1-SNAPSHOT.jar ./green.wallet-0.0.1-SNAPSHOT.jar

RUN ls -la

ENTRYPOINT ["java","-jar","/green.wallet-0.0.1-SNAPSHOT.jar"]

EXPOSE 8081

