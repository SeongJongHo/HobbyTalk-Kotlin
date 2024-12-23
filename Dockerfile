# 1단계: 빌드
FROM gradle:8.11-jdk17 AS builder
WORKDIR /app
COPY ./ .
RUN gradle build --no-daemon -x test

# 2단계: 실행
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /app/build/libs/hobbytalk-0.0.1-SNAPSHOT.jar app.jar
CMD ["java", "-Dspring.profiles.active=local", "-jar", "app.jar"]