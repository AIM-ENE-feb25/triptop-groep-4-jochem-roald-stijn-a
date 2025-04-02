package com.triptop.externe_service_prototype.repository;

import com.triptop.externe_service_prototype.api.Request;
import com.triptop.externe_service_prototype.api.Response;

import java.util.Optional;

public interface CacheRepository {
    void save(Request key, Response response, int durationMs);
    Optional<Response> get(Request key);
}
