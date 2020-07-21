FROM node:14.5.0-stretch-slim
COPY ./webui/frontend /app
WORKDIR /app

RUN npm install

EXPOSE 8080
CMD ["npm", "start"]

#FROM maven:3-openjdk-8 as builder
#COPY . /app
#WORKDIR /app
#RUN mvn -pl :webui clean install

#FROM openjdk:8-jdk-slim
#WORKDIR /app
#RUN printenv
#COPY --from=builder /app/webui/target/webui-0.8.0-runner.jar .
#COPY --from=builder /app/webui/target/lib/ ./lib/

#ENTRYPOINT ["java", "-jar", "webui-0.8.0-runner.jar"]
