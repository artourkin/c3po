FROM maven:3-openjdk-8 as builder
COPY . /app
WORKDIR /app
RUN mvn -pl !webui clean install

FROM openjdk:8-jdk-slim
WORKDIR /app
RUN printenv
COPY --from=builder /app/rest/target/rest-0.8.0-runner.jar .

ENTRYPOINT ["java", "-jar", "rest-0.8.0-runner.jar"]
