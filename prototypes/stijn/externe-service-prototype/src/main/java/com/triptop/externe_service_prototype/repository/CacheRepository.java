package com.triptop.externe_service_prototype.repository;

import com.triptop.externe_service_prototype.api.Request;
import com.triptop.externe_service_prototype.api.Response;

import java.util.Optional;

public interface CacheRepository {
    void save(Request request, Response response, Integer keepForSeconds);
    Optional<Response> get(Request request);
}
