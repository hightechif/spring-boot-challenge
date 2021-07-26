FROM maven:3.8.1-jdk-11-slim AS MAVEN_BUILD
WORKDIR /build
COPY pom.xml .
# creates layer with maven dependencies
# first build will be significantly slower than subsequent
RUN mvn dependency:go-offline

COPY ./pom.xml /tmp/
COPY ./src /tmp/src/
WORKDIR /tmp/
RUN mvn clean package -Dmaven.test.skip=true

# For Java 11, try this
FROM adoptopenjdk/openjdk11:alpine-jre
ARG JAR_FILE=target/*.jar
ARG JAR_NAME=app.jar

WORKDIR /app

COPY --from=MAVEN_BUILD /tmp/${JAR_FILE} /app/${JAR_NAME}
ENTRYPOINT ["java","-jar","app.jar"]