package com.triptop.externe_service_prototype.repository;

import com.triptop.externe_service_prototype.api.Origin;
import com.triptop.externe_service_prototype.api.Request;
import com.triptop.externe_service_prototype.api.Response;
import com.triptop.externe_service_prototype.exception.NotImplementedException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.UnifiedJedis;

import java.util.HashMap;
import java.util.Optional;

@Repository
public class RedisCacheRepositoryImpl implements CacheRepository {
    UnifiedJedis unifiedjedis;

    public RedisCacheRepositoryImpl(@Value("${redis.url}") String redisUrl) {
        unifiedjedis = new UnifiedJedis(redisUrl);
    }

    @Override
    public void save(Request key, Response response, int durationMs) {
        System.out.println("Saving to Redis");
        String convertedRequestToString = convertRequestToString(key);
        JSONObject responseBody = response.Body();

        unifiedjedis.set(convertedRequestToString, responseBody.toString());
    }

    @Override
    public Optional<Response> get(Request key) {
        System.out.println("Getting from Redis");
        String convertedRequestToString = convertRequestToString(key);

        String responseBodyString = unifiedjedis.get(convertedRequestToString);
        if (responseBodyString == null) {
            System.out.println("No data found in Redis");
            return Optional.empty();
        }

        JSONObject responseBody = new JSONObject(responseBodyString);
        return Optional.of(new Response(200, responseBody, Origin.REDIS_CACHE));
    }

    private String convertRequestToString(Request request) {
        return "url:" + request.url() + ",headers:" + request.headers().toString() + ",body:" + request.body();
    }
}
