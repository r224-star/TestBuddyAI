import { useState } from "react";
import "./App.css";

// Live Render Cloud Backend URL
const API_BASE_URL = "https://testbuddyai-vmw8.onrender.com";

function App() {
  const [requirement, setRequirement] = useState("");
  const [testClass, setTestClass] = useState("com.testbuddy.tests.LoginTest");

  // Dynamic Autonomous Healing Inputs
  const [targetUrl, setTargetUrl] = useState("https://www.saucedemo.com");
  const [brokenLocator, setBrokenLocator] = useState("invalid_login_btn_id");
  const [hint, setHint] = useState("login-button");

  const [result, setResult] = useState(null);
  const [loading, setLoading] = useState(false);

  // Normal Test Execution (Standard TestNG Run)
  const runTests = async () => {
    if (!requirement.trim()) {
      setResult({
        status: "ERROR",
        message: "Please enter a testing requirement.",
      });
      return;
    }

    if (!testClass.trim()) {
      setResult({
        status: "ERROR",
        message: "Please enter a test class.",
      });
      return;
    }

    setLoading(true);
    setResult(null);

    try {
      const response = await fetch(`${API_BASE_URL}/api/tests/run`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          requirement: requirement.trim(),
          testClasses: [testClass.trim()],
        }),
      });

      if (!response.ok) {
        let errorMessage = `Backend returned HTTP ${response.status}`;
        try {
          const errorData = await response.json();
          if (errorData.message) {
            errorMessage = errorData.message;
          }
        } catch {
          // Ignore JSON parsing error
        }

        setResult({
          status: "ERROR",
          message: errorMessage,
        });
        return;
      }

      const data = await response.json();
      setResult(data);
    } catch (error) {
      console.error("TestBuddyAI API Error:", error);
      setResult({
        status: "ERROR",
        message:
          "Unable to connect to TestBuddyAI backend. Render instance might be waking up (takes ~30-50 seconds on free tier).",
      });
    } finally {
      setLoading(false);
    }
  };

  // Autonomous Self-Healing Test Execution with Dynamic Inputs & Screenshot Capture
  const runSelfHealingTest = async () => {
    if (!targetUrl.trim()) {
      setResult({
        status: "ERROR",
        message: "Please enter a valid target URL.",
      });
      return;
    }

    setLoading(true);
    setResult(null);

    try {
      const response = await fetch(
        `${API_BASE_URL}/api/tests/execute-healing-test`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            url: targetUrl.trim(),
            brokenLocator: brokenLocator.trim(),
            hint: hint.trim(),
          }),
        }
      );

      if (!response.ok) {
        throw new Error(`Backend returned HTTP ${response.status}`);
      }

      const data = await response.json();
      setResult({
        status: data.status,
        message: data.message,
        healed: data.healed,
        recoveredTag: data.recoveredTag,
        screenshot: data.screenshot,
        targetUrl: data.targetUrl || targetUrl,
      });
    } catch (error) {
      console.error("Self-Healing API Error:", error);
      setResult({
        status: "ERROR",
        message:
          "Unable to execute Self-Healing Test. Ensure cloud backend is active.",
      });
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="app">
      {/* ================= NAVBAR ================= */}
      <header className="navbar">
        <div className="brand">
          <div className="brand-icon">T</div>
          <div>
            <h1>TestBuddyAI</h1>
            <span>Autonomous Software Testing Agent</span>
          </div>
        </div>

        <div className="server-status">
          <span className="status-dot"></span>
          Backend Cloud Live (Render)
        </div>
      </header>

      {/* ================= MAIN ================= */}
      <main className="container">
        {/* ================= HERO ================= */}
        <section className="hero">
          <div className="badge">AI-POWERED TEST AUTOMATION</div>
          <h2>
            Test smarter.
            <br />
            <span>Ship with confidence.</span>
          </h2>
          <p>
            Describe what you want to test and TestBuddyAI will analyze the
            requirement, generate test cases, execute automation, and
            autonomously heal broken locators with visual proof.
          </p>
        </section>

        {/* ================= WORKSPACE ================= */}
        <section className="workspace">
          {/* ================= TEST INPUT CARD ================= */}
          <div className="card">
            <div className="card-header">
              <div>
                <h3>Start a Test Run</h3>
                <p>Configure dynamic testing requirements or healing targets.</p>
              </div>
              <div className="step-number">01</div>
            </div>

            {/* Requirement */}
            <label htmlFor="requirement">Testing Requirement</label>
            <textarea
              id="requirement"
              value={requirement}
              onChange={(e) => setRequirement(e.target.value)}
              placeholder="Example: Test the login functionality with valid and invalid credentials"
              rows={4}
              disabled={loading}
            />

            {/* Test Class */}
            <label htmlFor="testClass" style={{ marginTop: "10px" }}>
              Standard Test Class
            </label>
            <input
              id="testClass"
              type="text"
              value={testClass}
              onChange={(e) => setTestClass(e.target.value)}
              disabled={loading}
            />

            {/* Dynamic Self-Healing Options */}
            <div
              style={{
                marginTop: "16px",
                padding: "12px",
                background: "rgba(255, 255, 255, 0.04)",
                borderRadius: "8px",
                border: "1px solid rgba(255, 255, 255, 0.1)",
              }}
            >
              <h4 style={{ margin: "0 0 10px 0", fontSize: "14px", color: "#10b981" }}>
                ⚡ Autonomous Self-Healing Parameters
              </h4>

              <label htmlFor="targetUrl" style={{ fontSize: "12px" }}>
                Target Webpage URL
              </label>
              <input
                id="targetUrl"
                type="text"
                value={targetUrl}
                onChange={(e) => setTargetUrl(e.target.value)}
                placeholder="https://www.saucedemo.com"
                disabled={loading}
              />

              <label htmlFor="brokenLocator" style={{ fontSize: "12px", marginTop: "8px" }}>
                Simulated Broken Selector (ID)
              </label>
              <input
                id="brokenLocator"
                type="text"
                value={brokenLocator}
                onChange={(e) => setBrokenLocator(e.target.value)}
                placeholder="invalid_login_btn_id"
                disabled={loading}
              />

              <label htmlFor="hint" style={{ fontSize: "12px", marginTop: "8px" }}>
                AI Recovery Semantic Hint
              </label>
              <input
                id="hint"
                type="text"
                value={hint}
                onChange={(e) => setHint(e.target.value)}
                placeholder="login-button"
                disabled={loading}
              />
            </div>

            {/* Action Buttons */}
            <div style={{ display: "flex", gap: "10px", marginTop: "16px" }}>
              <button
                className="run-button"
                onClick={runTests}
                disabled={loading}
                style={{ flex: 1 }}
              >
                {loading ? "Running Tests..." : "Run Standard Tests →"}
              </button>

              <button
                className="run-button"
                onClick={runSelfHealingTest}
                disabled={loading}
                style={{
                  flex: 1,
                  background:
                    "linear-gradient(135deg, #10b981 0%, #059669 100%)",
                }}
              >
                {loading ? "Healing & Inspecting DOM..." : "Run Self-Healing Test ⚡"}
              </button>
            </div>
          </div>

          {/* ================= RESULT CARD ================= */}
          <div className="card result-card">
            <div className="card-header">
              <div>
                <h3>Execution Result</h3>
                <p>Live response from TestBuddyAI.</p>
              </div>
              <div className="step-number">02</div>
            </div>

            {/* ================= EMPTY STATE ================= */}
            {!result && !loading && (
              <div className="empty-state">
                <div className="empty-icon">✓</div>
                <h4>Ready to test</h4>
                <p>
                  Enter a requirement or click{" "}
                  <strong>Run Self-Healing Test</strong>.
                </p>
              </div>
            )}

            {/* ================= LOADING STATE ================= */}
            {loading && (
              <div className="empty-state">
                <div className="loader"></div>
                <h4>TestBuddyAI is working...</h4>
                <p>
                  Executing tests and running autonomous DOM heuristic scans.
                </p>
              </div>
            )}

            {/* ================= RESULT ================= */}
            {result && !loading && (
              <div className="result">
                <div
                  className={`result-status ${
                    result.status === "COMPLETED" || result.status === "SUCCESS"
                      ? "success"
                      : "error"
                  }`}
                >
                  <span>
                    {result.status === "COMPLETED" || result.status === "SUCCESS"
                      ? "✓"
                      : "!"}
                  </span>
                  <div>
                    <strong>{result.status || "UNKNOWN"}</strong>
                    <p>{result.message || "Test execution completed."}</p>
                  </div>
                </div>

                {/* Self-Healing Success Tag, Visual Screenshot & ExtentReport Link */}
                {result.healed && (
                  <div
                    style={{
                      marginTop: "12px",
                      padding: "12px",
                      borderRadius: "8px",
                      background: "rgba(16, 185, 129, 0.15)",
                      border: "1px solid #10b981",
                      color: "#10b981",
                      fontSize: "13px",
                    }}
                  >
                    <div>
                      ⚡ <strong>Autonomous Recovery Active:</strong> Broken
                      locator healed. Recovered Element:{" "}
                      <code>&lt;{result.recoveredTag || "element"}&gt;</code>
                    </div>

                    {/* Live Highlighted Element Screenshot */}
                    {result.screenshot && (
                      <div style={{ marginTop: "14px" }}>
                        <p
                          style={{
                            fontWeight: "700",
                            marginBottom: "8px",
                            color: "#10b981",
                            fontSize: "12px",
                          }}
                        >
                          📸 Live Visual Proof (Healed Element Highlighted in Green):
                        </p>
                        <img
                          src={`data:image/png;base64,${result.screenshot}`}
                          alt="Healed Element Visual Proof"
                          style={{
                            width: "100%",
                            maxHeight: "260px",
                            objectFit: "contain",
                            borderRadius: "6px",
                            border: "1px solid rgba(16, 185, 129, 0.4)",
                            backgroundColor: "#000",
                          }}
                        />
                      </div>
                    )}

                    <div style={{ marginTop: "12px" }}>
                      <a
                        href={`${API_BASE_URL}/reports/SelfHealingTestReport.html`}
                        target="_blank"
                        rel="noopener noreferrer"
                        style={{
                          display: "inline-block",
                          padding: "8px 14px",
                          backgroundColor: "#10b981",
                          color: "#0f172a",
                          fontWeight: "700",
                          borderRadius: "6px",
                          textDecoration: "none",
                          fontSize: "12px",
                        }}
                      >
                        📊 View Visual HTML ExtentReport ↗
                      </a>
                    </div>
                  </div>
                )}

                {/* Standard Test Details */}
                {result.testName && (
                  <div className="result-detail">
                    <strong>Test Name:</strong>
                    <span>{result.testName}</span>
                  </div>
                )}

                {result.totalTests !== undefined && (
                  <div className="result-detail">
                    <strong>Total Tests:</strong>
                    <span>{result.totalTests}</span>
                  </div>
                )}

                {result.passedTests !== undefined && (
                  <div className="result-detail">
                    <strong>Passed:</strong>
                    <span>{result.passedTests}</span>
                  </div>
                )}

                {result.failedTests !== undefined && (
                  <div className="result-detail">
                    <strong>Failed:</strong>
                    <span>{result.failedTests}</span>
                  </div>
                )}
              </div>
            )}
          </div>
        </section>

        {/* ================= FEATURES ================= */}
        <section className="features">
          <div>
            <span>01</span>
            <h3>AI Analysis</h3>
            <p>Understand testing requirements automatically.</p>
          </div>

          <div>
            <span>02</span>
            <h3>Test Generation</h3>
            <p>Generate structured test cases from requirements.</p>
          </div>

          <div>
            <span>03</span>
            <h3>Self-Healing Engine</h3>
            <p>Dynamically recover from broken locators at runtime.</p>
          </div>

          <div>
            <span>04</span>
            <h3>Reporting & Visuals</h3>
            <p>Analyze results, capture live element snapshots, and generate defect reports.</p>
          </div>
        </section>
      </main>
    </div>
  );
}

export default App;