package com.triptop.externe_service_prototype.service;

import com.triptop.externe_service_prototype.api.Request;
import com.triptop.externe_service_prototype.api.ExternalAPIHandler;
import com.triptop.externe_service_prototype.api.Response;
import com.triptop.externe_service_prototype.exception.NotFoundException;
import com.triptop.externe_service_prototype.exception.RequestFailedException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Optional;

@Service
public class FlightsServiceImpl implements FlightsService {
    private final ExternalAPIHandler externalAPIHandler;
    private final String rapidapiKey;

    @Autowired
    public FlightsServiceImpl(ExternalAPIHandler externalAPIHandler, @Value("${rapidapi.key}") String rapidapiKey) {
        this.externalAPIHandler = externalAPIHandler;
        this.rapidapiKey = rapidapiKey;
    }

    @Override
    public double getFlightPrice(String origin, String destination, String departureDate, String returnDate) {
        String originId = getAirportId(origin);
        String destinationId = getAirportId(destination);

        Request request = new Request(
                "https://booking-com15.p.rapidapi.com/api/v1/flights/searchFlights?fromId=" + originId + "&toId=" + destinationId + "&departDate=" + departureDate + "&returnDate=" + returnDate + "&sort=BEST&cabinClass=ECONOMY&currency_code=EUR",
                getCommonHeaders()
        );

        // TODO: dataMeyGetOutdated should be true. False for testing
        Optional<Response> response = externalAPIHandler.sendRequest(request, false);

        if (response.isPresent()) {
            if (response.get().statusCode() < 200 || response.get().statusCode() >= 300) {
                throw new RequestFailedException("Request failed with status code " + response.get().statusCode());
            }

            System.out.println("Received response from " + response.get().origin());

            JSONObject minPriceInfo = response.get().body().getJSONObject("data").getJSONObject("aggregation").getJSONObject("minPrice");

            int units = minPriceInfo.getInt("units");
            int nanos = minPriceInfo.getInt("nanos");

            return units + nanos / 1_000_000_000.0;
        } else {
            throw new NotFoundException("No flights found from " + origin + " to " + destination + " on " + departureDate + " and back on " + returnDate);
        }
    }

    @Override
    public String getAirportId(String query) {
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);

        // Create a request object
        Request request = new Request(
                "https://booking-com15.p.rapidapi.com/api/v1/flights/searchDestination?query=" + encodedQuery,
                getCommonHeaders()
        );
        // Send the request to the apiHandler
        Optional<Response> response = externalAPIHandler.sendRequest(request, false);

        if (response.isPresent()) {
            if (response.get().statusCode() < 200 || response.get().statusCode() >= 300) {
                throw new RequestFailedException("Request failed with status code " + response.get().statusCode());
            }

            System.out.println("Received response from " + response.get().origin());

            return response.get().body().getJSONArray("data").getJSONObject(0).getString("id");
        } else {
            throw new NotFoundException("Airport with name " + query + " not found.");
        }
    }

    private HashMap<String, String> getCommonHeaders() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("x-rapidapi-key", rapidapiKey);
        headers.put("x-rapidapi-host", "booking-com15.p.rapidapi.com");
        return headers;
    }
}
