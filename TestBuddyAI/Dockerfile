# Stage 1: Build the JAR directly inside TestBuddyAI project folder
FROM maven:3.9.6-eclipse-temurin-17-alpine AS builder
WORKDIR /app

# Copy entire repository
COPY . .

# Build inside the actual Spring Boot subfolder
WORKDIR /app/TestBuddyAI
RUN mvn clean package -Dmaven.test.skip=true -Dstart-class=com.testbuddy.TestBuddyAiApplication -B

# Stage 2: Runtime image with Chromium and Selenium
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

# Copy generated jar from the TestBuddyAI build
COPY --from=builder /app/TestBuddyAI/target/*.jar app.jar

RUN mkdir -p /app/reports

EXPOSE 8081

ENTRYPOINT ["java", "-Djava.awt.headless=true", "-jar", "app.jar"]