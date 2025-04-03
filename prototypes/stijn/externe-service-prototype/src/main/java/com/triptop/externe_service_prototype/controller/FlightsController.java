package com.triptop.externe_service_prototype.controller;

import com.triptop.externe_service_prototype.service.FlightsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/flights")
public class FlightsController {
    private final FlightsService flightsService;

    public FlightsController(FlightsService flightsService) {
        this.flightsService = flightsService;
    }

    @GetMapping("/getAirportId")
    public ResponseEntity<String> getAirportId(@RequestParam String query) {
        return ResponseEntity.ok(flightsService.getAirportId(query));
    }

    @GetMapping("/getFlightPrice")
    public ResponseEntity<Double> getFlightPrice(@RequestParam String origin, @RequestParam String destination, @RequestParam String departureDate, @RequestParam String returnDate) {
        return ResponseEntity.ok(flightsService.getFlightPrice(origin, destination, departureDate, returnDate));
    }
}
