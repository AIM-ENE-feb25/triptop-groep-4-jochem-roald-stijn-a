package com.triptop.externe_service_prototype.service;

public interface FlightsService {
    String getAirportId(String query);
    double getFlightPrice(String origin, String destination, String departureDate, String returnDate);
}
