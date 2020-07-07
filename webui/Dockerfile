FROM maven:3-openjdk-8 as builder
COPY . /app
WORKDIR /app
RUN mvn -pl :webui clean install

FROM openjdk:8-jdk-slim
WORKDIR /app
RUN printenv
COPY --from=builder /app/webui/target/webui-0.8.0.jar .
COPY --from=builder /app/webui/target/lib/ ./lib/

ENTRYPOINT ["java", "-jar", "webui-0.8.0-runner.jar"]
