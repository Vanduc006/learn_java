# Stage 1: Build
FROM gradle:8.7-jdk17 AS build
WORKDIR /projectY
COPY . .
RUN gradle bootJar --no-daemon

# Stage 2: Runtime
FROM eclipse-temurin:17-jre-jammy
WORKDIR /projectY
COPY --from=build /projectY/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]

