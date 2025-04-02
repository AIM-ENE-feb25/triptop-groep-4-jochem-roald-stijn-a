package com.triptop.externe_service_prototype.api;

import java.util.HashMap;

/**
 * Represents an endpoint for an external API call.
 * This object holds all the data needed to send a request.
 *
 * @param url The URL of the endpoint, including any query parameters.
 * @param headers The headers to be included in the request.
 * @param body The body of the request, if any.
 */
public record Request(String url, HashMap<String, String> headers, String body) {
}
