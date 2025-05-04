FROM eclipse-temurin:21.0.7_6-jdk-alpine AS builder

WORKDIR /usr/app

COPY . .

RUN ./gradlew clean bootJar --no-daemon

FROM eclipse-temurin:21.0.7_6-jdk-alpine

COPY --from=builder /usr/app/build/libs/application.jar /opt/app/application.jar

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

EXPOSE 80

CMD ["sh", "-c", "java -jar /opt/app/application.jar"]