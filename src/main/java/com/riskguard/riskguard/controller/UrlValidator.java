package com.riskguard.riskguard.controller;

import java.net.URI;

public class UrlValidator {

    public static boolean isValidUrl(String url) {

        if (url == null || url.isBlank()) {
            return false;
        }

        try {
            URI uri = new URI(url);

            if (uri.getScheme() == null || uri.getHost() == null) {
                return false;
            }

            return uri.getScheme().equalsIgnoreCase("http")
                    || uri.getScheme().equalsIgnoreCase("https");

        } catch (Exception e) {
            return false;
        }
    }
}
