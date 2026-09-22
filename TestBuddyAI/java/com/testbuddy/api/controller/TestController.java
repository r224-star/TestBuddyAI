package com.testbuddy.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.testbuddy.ai.TestingOrchestrator.TestExecutionSummary;
import com.testbuddy.api.dto.TestRunRequest;
import com.testbuddy.api.dto.TestRunResponse;
import com.testbuddy.api.service.TestExecutionService;

@RestController
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174"})
@RequestMapping("/api/tests")
public class TestController {

    private final TestExecutionService testExecutionService;

    public TestController(
            TestExecutionService testExecutionService) {

        this.testExecutionService =
                testExecutionService;
    }

    @PostMapping("/run")
    public ResponseEntity<TestRunResponse> runTest(
            @RequestBody TestRunRequest request) {

        // ==============================
        // VALIDATE REQUEST
        // ==============================

        if (request == null) {

            return ResponseEntity.badRequest()
                    .body(
                            new TestRunResponse(
                                    "FAILED",
                                    "Request body is required.",
                                    0,
                                    0,
                                    0,
                                    0
                            )
                    );
        }

        if (request.getRequirement() == null
                || request.getRequirement().isBlank()) {

            return ResponseEntity.badRequest()
                    .body(
                            new TestRunResponse(
                                    "FAILED",
                                    "Requirement is required.",
                                    0,
                                    0,
                                    0,
                                    0
                            )
                    );
        }

        if (request.getTestClasses() == null
                || request.getTestClasses().isEmpty()) {

            return ResponseEntity.badRequest()
                    .body(
                            new TestRunResponse(
                                    "FAILED",
                                    "At least one test class is required.",
                                    0,
                                    0,
                                    0,
                                    0
                            )
                    );
        }

        // ==============================
        // EXECUTE TEST
        // ==============================

        try {

            TestExecutionSummary summary =
                    testExecutionService.runTest(
                            request.getRequirement(),
                            request.getTestClasses()
                    );

            return ResponseEntity.ok(
                    new TestRunResponse(
                            "COMPLETED",
                            "Test execution completed successfully.",
                            summary.getTotal(),
                            summary.getPassed(),
                            summary.getFailed(),
                            summary.getSkipped()
                    )
            );

        } catch (Exception e) {

            e.printStackTrace();

            String errorMessage =
                    e.getMessage();

            if (errorMessage == null
                    || errorMessage.isBlank()) {

                errorMessage =
                        e.getClass().getName();
            }

            return ResponseEntity.internalServerError()
                    .body(
                            new TestRunResponse(
                                    "FAILED",
                                    errorMessage,
                                    0,
                                    0,
                                    0,
                                    0
                            )
                    );
        }
    }
}