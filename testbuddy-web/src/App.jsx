import { useState } from "react";
import "./App.css";

function App() {
  const [requirement, setRequirement] = useState("");
  const [testClass, setTestClass] = useState("com.testbuddy.tests.LoginTest");
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
      const response = await fetch("http://localhost:8081/api/tests/run", {
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
          "Unable to connect to TestBuddyAI backend. Make sure Spring Boot is running on port 8081.",
      });
    } finally {
      setLoading(false);
    }
  };

  // Autonomous Self-Healing Test Execution
  const runSelfHealingTest = async () => {
    setLoading(true);
    setResult(null);

    try {
      const response = await fetch(
        "http://localhost:8081/api/tests/execute-healing-test",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            url: "https://www.saucedemo.com",
            hint: "login-button",
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
      });
    } catch (error) {
      console.error("Self-Healing API Error:", error);
      setResult({
        status: "ERROR",
        message:
          "Unable to execute Self-Healing Test. Ensure backend is running on port 8081.",
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
          Backend Online (Port 8081)
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
            autonomously heal broken locators.
          </p>
        </section>

        {/* ================= WORKSPACE ================= */}
        <section className="workspace">
          {/* ================= TEST INPUT CARD ================= */}
          <div className="card">
            <div className="card-header">
              <div>
                <h3>Start a Test Run</h3>
                <p>Describe your application testing requirement.</p>
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
              rows={6}
              disabled={loading}
            />

            {/* Test Class */}
            <label htmlFor="testClass">Test Class</label>
            <input
              id="testClass"
              type="text"
              value={testClass}
              onChange={(e) => setTestClass(e.target.value)}
              disabled={loading}
            />

            {/* Action Buttons */}
            <div style={{ display: "flex", gap: "10px", marginTop: "15px" }}>
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
                  background: "linear-gradient(135deg, #10b981 0%, #059669 100%)",
                }}
              >
                {loading ? "Healing & Running..." : "Run Self-Healing Test ⚡"}
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

                {/* Self-Healing Success Tag */}
                {result.healed && (
                  <div
                    style={{
                      marginTop: "12px",
                      padding: "10px",
                      borderRadius: "8px",
                      background: "rgba(16, 185, 129, 0.15)",
                      border: "1px solid #10b981",
                      color: "#10b981",
                      fontSize: "13px",
                    }}
                  >
                    ⚡ <strong>Autonomous Recovery Active:</strong> Broken
                    locator healed. Recovered Element:{" "}
                    <code>&lt;{result.recoveredTag || "element"}&gt;</code>
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
            <h3>Reporting</h3>
            <p>Analyze results and generate defect reports.</p>
          </div>
        </section>
      </main>
    </div>
  );
}

export default App;