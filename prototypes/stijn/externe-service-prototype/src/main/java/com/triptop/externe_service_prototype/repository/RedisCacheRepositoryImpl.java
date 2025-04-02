package com.triptop.externe_service_prototype.repository;

import com.triptop.externe_service_prototype.api.Endpoint;
import com.triptop.externe_service_prototype.api.Response;
import com.triptop.externe_service_prototype.exception.NotImplementedException;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class RedisCacheRepositoryImpl implements CacheRepository {
    @Override
    public void save(Endpoint key, Response response, int durationMs) {
        System.out.println("Saving to Redis");
        // throw new NotImplementedException();
    }

    @Override
    public Optional<Response> get(Endpoint key) {
        throw new NotImplementedException();
        // return Optional.empty();
    }
}
