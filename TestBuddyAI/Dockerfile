# Stage 1: Build Java Application
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Runtime Environment with Headless Chrome
FROM eclipse-temurin:17-jre
WORKDIR /app

# Install Headless Chromium and Drivers
RUN apt-get update && apt-get install -y \
    chromium \
    chromium-driver \
    && rm -rf /var/lib/apt/lists/*

ENV CHROME_BIN=/usr/bin/chromium
ENV CHROMEDRIVER_BIN=/usr/bin/chromedriver

# Copy jar from build stage
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]