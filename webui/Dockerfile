FROM openjdk:8-jdk-slim as builder
COPY . /app
WORKDIR /app
RUN ./mvnw -pl :webui clean install

FROM openjdk:8-jre-slim
WORKDIR /app
RUN printenv
COPY --from=builder /app/target/webui-0.8.0-runner.jar .

ENTRYPOINT ["java", "-jar", "webui-0.8.0-runner.jar"]
