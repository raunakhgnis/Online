package com.example.simpleauction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry; // Ensure this import is present
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.util.Arrays;
import java.util.stream.Collectors;

@SpringBootApplication
public class SimpleAuctionBackendApplication { // Renamed from SimpleAuctionApplication based on your code

    public static void main(String[] args) {
        SpringApplication.run(SimpleAuctionBackendApplication.class, args);
        // The Bean definition should NOT be here
    }

    // **** MOVE THE BEAN DEFINITION HERE ****
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                // Allow common dev origins by default. Can be overridden with ALLOWED_ORIGINS env var
                String env = System.getenv("ALLOWED_ORIGINS");
                String[] allowed;
                if (env != null && !env.isBlank()) {
                    allowed = Arrays.stream(env.split(","))
                            .map(String::trim)
                            .filter(s -> !s.isEmpty())
                            .toArray(String[]::new);
                } else {
                    allowed = new String[]{
                            "http://localhost:5173",
                            "http://127.0.0.1:5173",
                            "http://localhost:3000",
                            "http://127.0.0.1:3000"
                    };
                }

                // Log the allowed origins in dev for easier debugging (console)
                System.out.println("[CORS] Allowing origins: " + Arrays.stream(allowed).collect(Collectors.joining(", ")));

                registry.addMapping("/api/**") // Allow CORS for all paths under /api/
                        .allowedOrigins(allowed)
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allowed HTTP methods
                        .allowedHeaders("*") // Allow all headers
                        .allowCredentials(true); // Important if you handle cookies/auth headers
            }
        };
    }
    // **** END OF MOVED BEAN DEFINITION ****

}
