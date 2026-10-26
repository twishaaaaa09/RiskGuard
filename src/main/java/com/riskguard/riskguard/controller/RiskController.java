package com.riskguard.riskguard.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RiskController {

    @GetMapping("/api/risks")
    public String getRisks(@RequestParam String url) {

        if (!UrlValidator.isValidUrl(url)) {
            return "Invalid URL. Please enter a valid HTTP or HTTPS URL.";
        }

        return "URL accepted for security scanning: " + url;
    }
}