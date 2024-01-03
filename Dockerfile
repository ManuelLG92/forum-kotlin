FROM gradle:8.5-jdk-alpine as builder
USER root
WORKDIR /builder
ADD . /builder
RUN gradle build

FROM openjdk:21-jdk-slim
WORKDIR /app
EXPOSE 8080
COPY --from=builder /builder/build/libs/forum-kotlin-0.0.1.jar app.jar
CMD ["java", "-jar", "app.jar"]

