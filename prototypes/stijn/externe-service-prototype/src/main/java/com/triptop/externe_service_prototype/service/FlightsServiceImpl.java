package com.triptop.externe_service_prototype.service;

import com.triptop.externe_service_prototype.api.Endpoint;
import com.triptop.externe_service_prototype.api.ExternalAPIHandler;
import com.triptop.externe_service_prototype.api.Response;
import com.triptop.externe_service_prototype.exception.NotFoundException;
import com.triptop.externe_service_prototype.exception.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

@Service
public class FlightsServiceImpl implements FlightsService {
    private final ExternalAPIHandler externalAPIHandler;

    @Autowired
    public FlightsServiceImpl(ExternalAPIHandler externalAPIHandler) {
        this.externalAPIHandler = externalAPIHandler;
    }

    @Override
    public double getFlightPrice(String origin, String destination, Date departureDate, Date returnDate) {
        System.out.println("getFlightPrice called with origin: " + origin + ", destination: " + destination + ", departureDate: " + departureDate + ", returnDate: " + returnDate);
        throw new NotImplementedException();
    }

    @Override
    public String getAirportId(String airportName) {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("x-rapidapi-key", "92b62a4079mshe417d603a8d8c05p1bbb0fjsn42969109f91c");
        headers.put("x-rapidapi-host", "booking-com15.p.rapidapi.com");

        Endpoint endpoint = new Endpoint(
                "https://booking-com15.p.rapidapi.com/api/v1/flights/searchDestination?query=" + airportName,
                headers,
                null
        );
        Optional<Response> response = externalAPIHandler.call(endpoint);

        if (response.isPresent()) {
            return response.get().Body().getJSONArray("data").getJSONObject(0).getString("id");
        } else {
            throw new NotFoundException("Airport with name " + airportName + " not found.");
        }
    }
}
