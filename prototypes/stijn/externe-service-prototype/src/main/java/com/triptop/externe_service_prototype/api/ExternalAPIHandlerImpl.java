package com.triptop.externe_service_prototype.api;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.triptop.externe_service_prototype.exception.NotImplementedException;
import com.triptop.externe_service_prototype.repository.CacheRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExternalAPIHandlerImpl implements ExternalAPIHandler {
    private final CacheRepository cacheRepository;

    @Autowired
    public ExternalAPIHandlerImpl(CacheRepository cacheRepository) {
        this.cacheRepository = cacheRepository;
    }

    public Optional<Response> call(Endpoint endpoint) {
        Optional<Response> apiResponse = callExternalAPI(endpoint);

        if (apiResponse.isPresent()) {
            cacheRepository.save(endpoint, apiResponse.get(), 1000);
            return apiResponse;
        }

        return cacheRepository.get(endpoint);
    }

    private Optional<Response> callExternalAPI(Endpoint endpoint) {
        String url = endpoint.url();
        Map<String, String> headers = endpoint.headers();

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
