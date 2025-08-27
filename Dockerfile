FROM eclipse-temurin:22-jdk AS build

WORKDIR /app

COPY target/snaplink-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 3535

ENTRYPOINT ["java","-jar","app.jar"]
