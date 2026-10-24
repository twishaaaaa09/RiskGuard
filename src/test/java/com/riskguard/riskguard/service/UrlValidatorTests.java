package com.riskguard.riskguard.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UrlValidatorTests {

    private final UrlValidator validator = new UrlValidator();

    @Test
    void acceptsPlainHttpAndHttpsUrls() {
        assertTrue(validator.validate("https://example.com").valid());
        assertTrue(validator.validate("http://example.com/path?query=1").valid());
        assertTrue(validator.validate("  https://example.com  ").valid(), "surrounding whitespace is trimmed");
        assertTrue(validator.validate("https://sub.domain.example.co.uk/a/b#c").valid());
        assertTrue(validator.validate("http://localhost:8080/app").valid(), "localhost needs no dot");
        assertTrue(validator.validate("http://192.168.1.10/admin").valid(), "bare IP hosts are fine");
    }

    @Test
    void rejectsEmptyAndBlankInput() {
        assertFalse(validator.validate(null).valid());
        assertFalse(validator.validate("").valid());
        assertFalse(validator.validate("   ").valid());
    }

    @Test
    void rejectsMissingProtocolOrHost() {
        var noScheme = validator.validate("example.com");
        assertFalse(noScheme.valid());
        assertTrue(noScheme.error().contains("http"));

        var noHost = validator.validate("https:///just/a/path");
        assertFalse(noHost.valid());
        assertTrue(noHost.error().contains("host"));
    }

    @Test
    void rejectsUnsupportedProtocols() {
        for (String url : new String[] { "ftp://example.com/file", "file:///etc/passwd", "javascript:alert(1)" }) {
            var result = validator.validate(url);
            assertFalse(result.valid(), url + " should be rejected");
            assertTrue(result.error().contains("only http and https"), url);
        }
    }

    @Test
    void rejectsMalformedInput() {
        assertFalse(validator.validate("https://exa mple.com").valid(), "space inside the URL");
        assertFalse(validator.validate("http://").valid(), "scheme but nothing else");
    }

    @Test
    void errorMessagesAreSentences() {
        for (String url : new String[] { null, "example.com", "ftp://x", "https://" }) {
            String error = validator.validate(url).error();
            assertTrue(error != null && error.endsWith("."), "error should be a sentence: " + error);
        }
    }
}
