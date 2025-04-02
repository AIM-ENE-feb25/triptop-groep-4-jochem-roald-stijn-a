package prototype.jochem.triptop.controller;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import prototype.jochem.triptop.domain.Journey;
import prototype.jochem.triptop.domain.Transport;
import prototype.jochem.triptop.service.JourneyService;
import prototype.jochem.triptop.strategy.JourneyStrategy;

import java.util.List;

@RestController
public class JourneyController {
    private final JourneyService journeyService;

    @Autowired
    public JourneyController(JourneyService journeyService) {
        this.journeyService = journeyService;
    }

    @GetMapping("/journeys")
    public List<Journey> getAllJourneys(
            @RequestParam String strategy,
            @RequestParam(required = false) String origin,
            @RequestParam(required = false) String destination,
            @RequestParam(required = false) String departureDate,
            @RequestParam(required = false) String returnDate,
            @RequestParam(required = false) double price,
            @RequestParam(required = false) Transport transport) throws UnirestException {
        journeyService.setJourneyStrategy(strategy);
        return journeyService.getJourneys(origin, destination, departureDate, returnDate, price, transport);
    }

}
