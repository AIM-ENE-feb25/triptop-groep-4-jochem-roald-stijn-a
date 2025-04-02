package com.triptop.externe_service_prototype.api;

import org.json.JSONObject;

public record Response(int StatusCode, JSONObject Body, Origin Origin) {
}
