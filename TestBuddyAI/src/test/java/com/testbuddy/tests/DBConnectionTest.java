package com.testbuddy.tests;

import com.testbuddy.utils.DBConnection;
import org.testng.annotations.Test;
import java.sql.Connection;

public class DBConnectionTest {

    @Test
    public void testConnection() throws Exception {
        Connection conn = DBConnection.getConnection();
        if (conn != null && !conn.isClosed()) {
            System.out.println("Database connected successfully!");
        }
        conn.close();
    }
}