package prototype.jochem.triptop.service;

import org.springframework.stereotype.Service;
import prototype.jochem.triptop.domain.Journey;
import prototype.jochem.triptop.domain.Transport;
import prototype.jochem.triptop.strategy.JourneyStrategy;

import java.util.List;

@Service
public interface JourneyService {
    void setJourneyStrategy(JourneyStrategy journeyStrategy);
    List<Journey> getJourneys(String origin, String destination, String departureDate, String returnDate, double price, Transport transport);
}
