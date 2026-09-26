FROM selenium/standalone-chrome:latest

USER root
RUN apt-get update && apt-get install -y openjdk-17-jre-headless && rm -rf /var/lib/apt/lists/*

WORKDIR /app
COPY target/*.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-Djava.awt.headless=true", "-jar", "app.jar"]