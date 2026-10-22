package com.testbuddy;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Render container aur local dono jagah ke reports folder ko map karta hai
        String reportPath = System.getProperty("user.dir") + File.separator + "reports" + File.separator;
        registry.addResourceHandler("/reports/**")
                .addResourceLocations("file:" + reportPath, "file:./reports/", "classpath:/static/reports/");
    }
}