package com.triptop.externe_service_prototype.repository;

import com.triptop.externe_service_prototype.api.Endpoint;
import com.triptop.externe_service_prototype.api.Response;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface CacheRepository {
    void save(Endpoint key, Response response, int durationMs);
    Optional<Response> get(Endpoint key);
}
