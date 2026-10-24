package com.riskguard.riskguard.controller;

import com.riskguard.riskguard.service.UrlValidator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RiskController {

    private final UrlValidator urlValidator = new UrlValidator();

    @GetMapping("/api/risks")
    public String getRisks() {
        return "RiskGuard API is working!";
    }

    /** Basic URL validation, the gate every scanned URL passes through first (#1). */
    @GetMapping("/api/validate")
    public UrlValidator.ValidationResult validateUrl(@RequestParam("url") String url) {
        return urlValidator.validate(url);
    }
}
