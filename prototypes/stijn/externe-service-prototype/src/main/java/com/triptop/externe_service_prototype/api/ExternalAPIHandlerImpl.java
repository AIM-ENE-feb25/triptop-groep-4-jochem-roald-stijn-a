package com.triptop.externe_service_prototype.api;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.triptop.externe_service_prototype.repository.CacheRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        if (dataMayGetOutdated) {
            Optional<Response> apiResponse = callExternalAPI(request);

            if (apiResponse.isEmpty()) {
                return cacheRepository.get(request);
            }

            cacheRepository.save(request, apiResponse.get(), 1000 * 60 * 60 * 24); // 24 hours
            return apiResponse;
        } else {
            Optional<Response> cachedResponse = cacheRepository.get(request);

            if (cachedResponse.isPresent()) {
                return cachedResponse;
            }

            Optional<Response> apiResponse = callExternalAPI(request);
            if (apiResponse.isPresent()) {
                cacheRepository.save(request, apiResponse.get(), 0);
            }

            return apiResponse;
        }
    }

    private Optional<Response> callExternalAPI(Request request) {
        String url = request.url();
        Map<String, String> headers = request.headers();

        System.out.println("Calling external API with URL: " + url);

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
