# Stage 1: Build the JAR using lightweight Alpine Maven
FROM maven:3.9.6-eclipse-temurin-17-alpine AS builder
WORKDIR /workspace

# Copy POM and download dependencies first (cached layer)
COPY pom.xml .
RUN mvn dependency:go-offline -B || true

# Copy source code and build clean JAR
COPY src ./src
RUN mvn clean package -DskipTests -B

# Stage 2: Runtime image with Chromium and Selenium drivers
FROM eclipse-temurin:17-jre-alpine

RUN apk update && apk add --no-cache \
    chromium \
    chromium-chromedriver \
    nss \
    freetype \
    harfbuzz \
    ttf-freefont

ENV CHROME_BIN=/usr/bin/chromium-browser
ENV CHROMEDRIVER_PATH=/usr/bin/chromedriver

WORKDIR /app

# Copy compiled jar from build stage
COPY --from=builder /workspace/target/*.jar app.jar

RUN mkdir -p /app/reports

EXPOSE 8081

ENTRYPOINT ["java", "-Djava.awt.headless=true", "-jar", "app.jar"]