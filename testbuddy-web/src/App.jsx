import { useState } from "react";
import "./App.css";

function App() {
  const [requirement, setRequirement] = useState("");
  const [testClass, setTestClass] = useState(
    "com.testbuddy.tests.LoginTest"
  );

  const [result, setResult] = useState(null);
  const [loading, setLoading] = useState(false);

  const runTests = async () => {
    // Validate requirement
    if (!requirement.trim()) {
      setResult({
        status: "ERROR",
        message: "Please enter a testing requirement.",
      });
      return;
    }

    // Validate test class
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
      const response = await fetch(
        "http://localhost:8080/api/tests/run",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            requirement: requirement.trim(),
            testClasses: [testClass.trim()],
          }),
        }
      );

      // Handle HTTP errors
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

      // Read backend response
      const data = await response.json();

      setResult(data);
    } catch (error) {
      console.error("TestBuddyAI API Error:", error);

      setResult({
        status: "ERROR",
        message:
          "Unable to connect to TestBuddyAI backend. Make sure Spring Boot is running on port 8080.",
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

          <div className="brand-icon">
            T
          </div>

          <div>
            <h1>TestBuddyAI</h1>

            <span>
              Autonomous Software Testing Agent
            </span>
          </div>

        </div>

        <div className="server-status">
          <span className="status-dot"></span>
          Backend Online
        </div>
      </header>


      {/* ================= MAIN ================= */}

      <main className="container">

        {/* ================= HERO ================= */}

        <section className="hero">

          <div className="badge">
            AI-POWERED TEST AUTOMATION
          </div>

          <h2>
            Test smarter.
            <br />
            <span>Ship with confidence.</span>
          </h2>

          <p>
            Describe what you want to test and TestBuddyAI will
            analyze the requirement, generate test cases,
            execute automation, and report the results.
          </p>

        </section>


        {/* ================= WORKSPACE ================= */}

        <section className="workspace">

          {/* ================= TEST INPUT CARD ================= */}

          <div className="card">

            <div className="card-header">

              <div>
                <h3>Start a Test Run</h3>

                <p>
                  Describe your application testing requirement.
                </p>
              </div>

              <div className="step-number">
                01
              </div>

            </div>


            {/* Requirement */}

            <label htmlFor="requirement">
              Testing Requirement
            </label>

            <textarea
              id="requirement"
              value={requirement}
              onChange={(e) => setRequirement(e.target.value)}
              placeholder="Example: Test the login functionality with valid and invalid credentials"
              rows={6}
              disabled={loading}
            />


            {/* Test Class */}

            <label htmlFor="testClass">
              Test Class
            </label>

            <input
              id="testClass"
              type="text"
              value={testClass}
              onChange={(e) => setTestClass(e.target.value)}
              disabled={loading}
            />


            {/* Run Button */}

            <button
              className="run-button"
              onClick={runTests}
              disabled={loading}
            >
              {loading ? (
                "Running Tests..."
              ) : (
                <>
                  Run Tests
                  <span>→</span>
                </>
              )}
            </button>

          </div>


          {/* ================= RESULT CARD ================= */}

          <div className="card result-card">

            <div className="card-header">

              <div>
                <h3>Execution Result</h3>

                <p>
                  Live response from TestBuddyAI.
                </p>
              </div>

              <div className="step-number">
                02
              </div>

            </div>


            {/* ================= EMPTY STATE ================= */}

            {!result && !loading && (
              <div className="empty-state">

                <div className="empty-icon">
                  ✓
                </div>

                <h4>
                  Ready to test
                </h4>

                <p>
                  Enter a requirement and click{" "}
                  <strong>Run Tests</strong>.
                </p>

              </div>
            )}


            {/* ================= LOADING STATE ================= */}

            {loading && (
              <div className="empty-state">

                <div className="loader"></div>

                <h4>
                  TestBuddyAI is working...
                </h4>

                <p>
                  Analyzing requirement and executing
                  your automation test.
                </p>

              </div>
            )}


            {/* ================= RESULT ================= */}

            {result && !loading && (
              <div className="result">

                <div
                  className={`result-status ${
                    result.status === "COMPLETED"
                      ? "success"
                      : "error"
                  }`}
                >

                  <span>
                    {result.status === "COMPLETED"
                      ? "✓"
                      : "!"}
                  </span>

                  <div>

                    <strong>
                      {result.status || "UNKNOWN"}
                    </strong>

                    <p>
                      {result.message ||
                        "Test execution completed."}
                    </p>

                  </div>

                </div>


                {/* Optional result details */}

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

            <h3>
              AI Analysis
            </h3>

            <p>
              Understand testing requirements automatically.
            </p>
          </div>


          <div>
            <span>02</span>

            <h3>
              Test Generation
            </h3>

            <p>
              Generate structured test cases from requirements.
            </p>
          </div>


          <div>
            <span>03</span>

            <h3>
              Automation
            </h3>

            <p>
              Execute Selenium and TestNG automation.
            </p>
          </div>


          <div>
            <span>04</span>

            <h3>
              Reporting
            </h3>

            <p>
              Analyze results and generate defect reports.
            </p>
          </div>

        </section>

      </main>

    </div>
  );
}

export default App;