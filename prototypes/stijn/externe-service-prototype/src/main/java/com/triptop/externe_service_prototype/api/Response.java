package com.triptop.externe_service_prototype.api;

import org.json.JSONObject;

/**
 * Represents an HTTP response with a status code, body, and origin.
 * @param statusCode The status code of the API response.
 * @param body The body of the response.
 * @param origin The origin of the response (e.g., EXTERNAL_API, REDIS_CACHE).
 */
public record Response(int statusCode, JSONObject body, Origin origin) {
}
