package com.triptop.externe_service_prototype.api;

import java.util.HashMap;

public record Request(String url, HashMap<String, String> headers) {
}
