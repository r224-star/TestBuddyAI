package com.testbuddy.automation;

import com.testbuddy.utils.DBConnection;
import com.testbuddy.utils.TestCase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TestCaseDAO {

    // CREATE - naya test case save karo
	public void saveTestCase(TestCase tc) throws Exception {

	    String sql = """
	        INSERT INTO test_cases (id, title, steps, expected_result)
	        VALUES (?, ?, ?, ?)
	        ON DUPLICATE KEY UPDATE
	            title = ?,
	            steps = ?,
	            expected_result = ?
	        """;

	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setString(1, tc.getId());
	        stmt.setString(2, tc.getTitle());
	        stmt.setString(3, tc.getSteps());
	        stmt.setString(4, tc.getExpectedResult());

	        // Existing test case ko update karega
	        stmt.setString(5, tc.getTitle());
	        stmt.setString(6, tc.getSteps());
	        stmt.setString(7, tc.getExpectedResult());

	        stmt.executeUpdate();
	    }
	}
    // CREATE - multiple test cases ek saath save karo
    public void saveTestCases(List<TestCase> testCases) throws Exception {
        for (TestCase tc : testCases) {
            saveTestCase(tc);
        }
        System.out.println(testCases.size() + " test cases saved to database.");
    }

    // READ - saare test cases wapas lao
    public List<TestCase> getAllTestCases() throws Exception {
        List<TestCase> testCases = new ArrayList<>();
        String sql = "SELECT * FROM test_cases";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                testCases.add(new TestCase(
                        rs.getString("id"),
                        rs.getString("title"),
                        rs.getString("steps"),
                        rs.getString("expected_result")
                ));
            }
        }
        return testCases;
    }

    // DELETE - ek test case ID se delete karo
    public void deleteTestCase(String id) throws Exception {
        String sql = "DELETE FROM test_cases WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.executeUpdate();
        }
    }
}