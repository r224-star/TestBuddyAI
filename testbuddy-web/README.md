# ⚡ TestBuddyAI - Autonomous Software Testing Agent

An intelligent QA automation framework featuring runtime locator self-healing, automated execution pipelines, and interactive HTML dashboards.

🔗 **Live Frontend:** [https://testbuddyai.vercel.app](https://testbuddyai.vercel.app)

## 🚀 Key Features

- **Runtime Self-Healing:** Recovers broken locators dynamically via heuristic DOM scanning.
- **Visual HTML ExtentReports:** Generates rich, dark-themed execution reports.
- **RESTful API:** Spring Boot 3.3.4 microservice running on port 8081.
- **Modern Dashboard:** React 18 + Vite live on Vercel.

## 💻 Setup & Run

- **Backend:** Run `TestBuddyApplication.java` (Port 8081)
- **Frontend:** `cd testbuddy-web && npm install && npm run dev`
- **Reports:** Open `http://localhost:8081/reports/SelfHealingTestReport.html`