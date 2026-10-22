package com.testbuddy.api.controller;

import java.io.File;
import java.nio.file.Files;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class ReportViewController {

    @GetMapping(value = {"/reports/SelfHealingTestReport.html", "/reports/**"}, produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<byte[]> serveReport() {
        try {
            String[] possiblePaths = {
                "reports/SelfHealingTestReport.html",
                "reports" + File.separator + "SelfHealingTestReport.html",
                System.getProperty("user.dir") + File.separator + "reports" + File.separator + "SelfHealingTestReport.html",
                "target/classes/static/reports/SelfHealingTestReport.html"
            };

            for (String path : possiblePaths) {
                File file = new File(path);
                if (file.exists() && file.isFile()) {
                    byte[] content = Files.readAllBytes(file.toPath());
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.TEXT_HTML);
                    return new ResponseEntity<>(content, headers, HttpStatus.OK);
                }
            }

            String notFoundHtml = "<html><body style='font-family:sans-serif;text-align:center;padding:50px;background:#0f172a;color:#f8fafc;'>"
                    + "<h2>Report File Not Generated Yet</h2>"
                    + "<p>Please execute a Self-Healing Test from the dashboard first to create this report.</p>"
                    + "</body></html>";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFoundHtml.getBytes());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(("<h3>Error loading report: " + e.getMessage() + "</h3>").getBytes());
        }
    }
}