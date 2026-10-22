# Stage 1: Build the Spring Boot application
FROM maven:3.9.6-eclipse-temurin-17-alpine AS builder
WORKDIR /build

# Copy root or sub-module files
COPY . .

# Run Maven build inside directory containing pom.xml
RUN if [ -f "TestBuddyAI/pom.xml" ]; then \
        cd TestBuddyAI && mvn clean package -DskipTests; \
    else \
        mvn clean package -DskipTests; \
    fi

# Stage 2: Runtime image with Chromium & Headless Drivers
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

# Copy the built jar from builder stage
COPY --from=builder /build/**/target/*.jar app.jar

# Ensure reports directory exists inside the container
RUN mkdir -p /app/reports

EXPOSE 8081

ENTRYPOINT ["java", "-Djava.awt.headless=true", "-jar", "app.jar"]