package com.riskguard.riskguard.service;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Basic URL validation, run before any URL reaches the scanning logic: the URL
 * must be absolute, use a supported protocol (http/https) and carry a host.
 */
public class UrlValidator {

    /** The outcome of a validation: valid URLs carry no error message. */
    public record ValidationResult(boolean valid, String error) {
        static final ValidationResult OK = new ValidationResult(true, null);
    }

    public ValidationResult validate(String raw) {
        if (raw == null || raw.isBlank()) {
            return new ValidationResult(false, "URL cannot be empty.");
        }
        String trimmed = raw.trim();
        if (trimmed.matches(".*[\\s\\p{Cntrl}].*")) {
            return new ValidationResult(false, "URL must not contain spaces or control characters.");
        }
        URI uri;
        try {
            uri = new URI(trimmed);
        } catch (URISyntaxException e) {
            return new ValidationResult(false, "This does not look like a valid URL: " + e.getReason() + ".");
        }
        String scheme = uri.getScheme();
        if (scheme == null) {
            return new ValidationResult(false, "URL must start with http:// or https://.");
        }
        if (!scheme.equalsIgnoreCase("http") && !scheme.equalsIgnoreCase("https")) {
            return new ValidationResult(false,
                    "Unsupported protocol '" + scheme + "': only http and https are supported.");
        }
        String host = uri.getHost();
        if (host == null || host.isBlank()) {
            return new ValidationResult(false, "URL must include a host name, e.g. https://example.com.");
        }
        return ValidationResult.OK;
    }
}
