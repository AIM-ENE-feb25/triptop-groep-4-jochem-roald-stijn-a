package com.triptop.externe_service_prototype.api;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.triptop.externe_service_prototype.exception.RequestFailedException;
import com.triptop.externe_service_prototype.repository.CacheRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;

@Component
public class ExternalAPIHandlerImpl implements ExternalAPIHandler {
    private final CacheRepository cacheRepository;

    @Autowired
    public ExternalAPIHandlerImpl(CacheRepository cacheRepository) {
        this.cacheRepository = cacheRepository;
    }

    public Optional<Response> sendRequest(Request request, boolean dataMayGetOutdated) {
        System.out.println();
        System.out.println("--------------------");
        System.out.println("sendRequest called with URL: " + request.url());

        if (dataMayGetOutdated) {
            System.out.println("Calling external API");
            Optional<Response> apiResponse = callExternalAPI(request);

            if (apiResponse.isEmpty()) {
                System.out.println("External API call failed. Checking cache");
                return cacheRepository.get(request);
            }

            System.out.println("Saving response to cache");
            cacheRepository.save(request, apiResponse.get(), 60 * 60 * 24); // 24 hours
            return apiResponse;
        } else {
            System.out.println("Checking cache");
            Optional<Response> cachedResponse = cacheRepository.get(request);

            if (cachedResponse.isPresent()) {
                return cachedResponse;
            }

            System.out.println("Cache is empty. Calling external API");
            Optional<Response> apiResponse = callExternalAPI(request);
            if (apiResponse.isPresent()) {
                System.out.println("Saving response to cache");
                cacheRepository.save(request, apiResponse.get(), 10); // 10 minutes for testing purposes. Should be null
            }

            return apiResponse;
        }
    }

    private Optional<Response> callExternalAPI(Request request) {
        String url = request.url();
        Map<String, String> headers = request.headers();

        try {
            HttpResponse<String> apiResponse = Unirest.get(url)
                    .headers(headers)
                    .asString();

            int statusCode = apiResponse.getStatus();
            JSONObject apiResponseBody = new JSONObject(apiResponse.getBody());

            return Optional.of(new Response(statusCode, apiResponseBody, Origin.EXTERNAL_API));
        } catch (UnirestException ue) {
            return Optional.empty();
        }
    }
}
