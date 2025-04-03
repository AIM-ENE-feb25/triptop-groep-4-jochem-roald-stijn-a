package com.triptop.externe_service_prototype.api;

import java.util.HashMap;

/**
 * Represents an HTTP request with a URL and headers.
 * @param url The URL of the request, including query parameters.
 * @param headers The headers of the request.
 */
public record Request(String url, HashMap<String, String> headers) {
}
