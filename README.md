\# ⚡ TestBuddyAI - Autonomous QA Testing Agent



An intelligent, autonomous test automation engine equipped with dynamic runtime locator self-healing, visual screenshot regression highlighting, interactive ExtentReports dashboards, a Spring Boot REST API, a modern React dashboard, and a containerized cloud deployment via Docker on Render and Vercel.



\---



\## 🌐 Live Deployments



\- \*\*Interactive Frontend (Vercel):\*\* \[https://testbuddyai.vercel.app](https://testbuddyai.vercel.app)

\- \*\*Containerized REST API (Render):\*\* \[https://testbuddyai-vmw8.onrender.com](https://testbuddyai-vmw8.onrender.com)

\- \*\*HTML ExtentReport Dashboard:\*\* \[https://testbuddyai-vmw8.onrender.com/reports/SelfHealingTestReport.html](https://testbuddyai-vmw8.onrender.com/reports/SelfHealingTestReport.html)



\---



\## 🛠️ Key Capabilities \& Architecture



\- \*\*Runtime Locator Self-Healing:\*\* Catches `NoSuchElementException` automatically, computes semantic similarity using attribute hints (ID, Name, CSS classes, Tag types), and recalculates broken selectors at runtime without breaking CI/CD pipelines.

\- \*\*Visual Evidence Highlighting:\*\* Automatically injects green border highlights onto recovered elements and streams in-memory Base64 full-page screenshots back to the UI in real-time.

\- \*\*Microservice Test Execution:\*\* Headless Chromium automated test runs exposed via Spring Boot REST endpoints (`/api/tests/execute-healing-test`).

\- \*\*Cloud-Ready Containerization:\*\* Dockerized using an ultra-lightweight Alpine Linux distribution (`eclipse-temurin:17-jre-alpine` + headless `chromium` and `chromium-chromedriver`), eliminating the memory overhead of multi-gigabyte browser images.

\- \*\*Single Page Application Dashboard:\*\* Built with React and Vite, featuring execution cards, custom URL/locator inputs, dynamic recovery logs, and live screenshot rendering.



\---



\## 💻 Tech Stack



\- \*\*Backend:\*\* Java 17, Spring Boot 3.3.4, Selenium WebDriver 4.19.1, ExtentReports 5.1.1, Maven

\- \*\*Frontend:\*\* React 18, Vite, Lucide Icons, CSS3 Modern Glassmorphism

\- \*\*Container \& Hosting:\*\* Docker, Alpine Linux, Render (Cloud Web Service), Vercel (SPA Hosting)



\---



\## 🚀 API Endpoints



\### 1. Health Check

`GET /api/tests/health`



\*\*Response:\*\*

```json

{

&#x20; "service": "TestBuddyAI Automation Engine",

&#x20; "status": "UP"

}

