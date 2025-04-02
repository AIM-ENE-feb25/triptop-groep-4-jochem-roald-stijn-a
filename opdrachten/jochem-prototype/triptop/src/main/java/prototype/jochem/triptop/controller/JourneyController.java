package prototype.jochem.triptop.controller;

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

    @GetMapping("/journeys/{strategy}")
    public List<Journey> getAllJourneys(
            @PathVariable("strategy") JourneyStrategy strategy,
            @RequestParam(required = false) String origin,
            @RequestParam(required = false) String destination,
            @RequestParam(required = false) String departureDate,
            @RequestParam(required = false) String returnDate,
            @RequestParam(required = false) Double price,
            @RequestParam(required = false) Transport transport) {
        journeyService.setJourneyStrategy(strategy);
        return journeyService.getJourneys(origin, destination, departureDate, returnDate, price, transport);
    }

}
