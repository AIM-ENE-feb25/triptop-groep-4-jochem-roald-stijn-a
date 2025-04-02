package com.triptop.externe_service_prototype.repository;

import com.triptop.externe_service_prototype.api.Origin;
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
    public void save(String url, Response response, Integer keepForSeconds) {
        String value = response.body().toString();

        if (keepForSeconds == null) {
            unifiedJedis.set(url, value);
            return;
        }

        unifiedJedis.setex(url, keepForSeconds, value);
    }

    @Override
    public Optional<Response> get(String url) {
        String responseBodyString = unifiedJedis.get(url);
        if (responseBodyString == null) {
            return Optional.empty();
        }

        JSONObject responseBody = new JSONObject(responseBodyString);
        return Optional.of(new Response(200, responseBody, Origin.REDIS_CACHE));
    }
}
