package com.triptop.externe_service_prototype.repository;

import com.triptop.externe_service_prototype.api.Origin;
import com.triptop.externe_service_prototype.api.Request;
import com.triptop.externe_service_prototype.api.Response;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.UnifiedJedis;

import java.util.Optional;

@Repository
public class RedisCacheRepositoryImpl implements CacheRepository {
    UnifiedJedis unifiedJedis;

    public RedisCacheRepositoryImpl(@Value("${redis.url}") String redisUrl) {
        unifiedJedis = new UnifiedJedis(redisUrl);
    }

    @Override
    public void save(Request request, Response response, Integer keepForSeconds) {
        String key = request.url();
        String value = response.Body().toString();

        if (keepForSeconds == null) {
            unifiedJedis.set(key, value);
            return;
        }

        unifiedJedis.setex(key, keepForSeconds, value);
    }

    @Override
    public Optional<Response> get(Request request) {
        String key = request.url();

        String responseBodyString = unifiedJedis.get(key);
        if (responseBodyString == null) {
            return Optional.empty();
        }

        JSONObject responseBody = new JSONObject(responseBodyString);
        return Optional.of(new Response(200, responseBody, Origin.REDIS_CACHE));
    }
}
