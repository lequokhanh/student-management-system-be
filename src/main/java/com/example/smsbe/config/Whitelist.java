package com.example.smsbe.config;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class Whitelist {
    private static final Map<String, String> WHITELIST = new HashMap<>();

    static {
        WHITELIST.put("/v1/auth/**", "ANY"); // "ANY" to allow all methods
        WHITELIST.put("/api/v1/public/**", "GET"); // Allow only GET method
        WHITELIST.put("/swagger-ui/**", "ANY");
        WHITELIST.put("/v3/api-docs/**", "ANY");
    }

    public static Map<String, String> get() {
        return WHITELIST;
    }
}