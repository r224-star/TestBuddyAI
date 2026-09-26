(
echo FROM eclipse-temurin:17-jre
echo RUN apt-get update ^&^& apt-get install -y --no-install-recommends chromium chromium-driver libnss3 libatk-bridge2.0-0 libx11-xcb1 libxcb-dri3-0 libdrm2 libgbm1 libasound2 fonts-liberation ^&^& rm -rf /var/lib/apt/lists/*
echo ENV CHROME_BIN=/usr/bin/chromium
echo ENV CHROMEDRIVER_PATH=/usr/bin/chromedriver
echo WORKDIR /app
echo COPY target/*.jar app.jar
echo EXPOSE 8081
echo ENTRYPOINT ["java", "-Djava.awt.headless=true", "-jar", "app.jar"]
) > "D:\Autonomous Software Testing Agent\TestBuddyAI\Dockerfile"