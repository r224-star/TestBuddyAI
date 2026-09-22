package com.testbuddy.automation;

import com.testbuddy.utils.DBConnection;
import com.testbuddy.utils.TestResult;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class TestResultDAO {

    public void saveResult(TestResult result) throws Exception {

        String sql = "INSERT INTO test_results (test_name, status, error_message) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, result.getTestName());
            stmt.setString(2, result.getStatus());
            stmt.setString(3, result.getErrorMessage());

            stmt.executeUpdate();
        }
    }
}