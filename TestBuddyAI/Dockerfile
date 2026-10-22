# Stage 1: Build the JAR
FROM maven:3.9.6-eclipse-temurin-17-alpine AS builder
WORKDIR /workspace

# Copy entire repository files
COPY . .

# Build JAR by locating pom.xml automatically and skipping all test compilation
RUN if [ -f "pom.xml" ]; then \
        mvn clean package -Dmaven.test.skip=true -B; \
    else \
        cd TestBuddyAI && mvn clean package -Dmaven.test.skip=true -B; \
    fi

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

# Copy the generated JAR regardless of folder level
COPY --from=builder /workspace/**/target/*.jar app.jar

RUN mkdir -p /app/reports

EXPOSE 8081

ENTRYPOINT ["java", "-Djava.awt.headless=true", "-jar", "app.jar"]