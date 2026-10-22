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
COPY TestBuddyAI/target/*.jar app.jar

RUN mkdir -p /app/reports

EXPOSE 8081

ENTRYPOINT ["java", "-Djava.awt.headless=true", "-jar", "app.jar"]