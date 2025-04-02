package com.triptop.externe_service_prototype.service;

import org.springframework.stereotype.Service;

import java.util.Date;

public interface FlightsService {
    String getAirportId(String airportName);
    double getFlightPrice(String origin, String destination, String departureDate, String returnDate);
}
