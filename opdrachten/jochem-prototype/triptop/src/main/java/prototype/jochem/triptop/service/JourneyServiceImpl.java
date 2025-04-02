package prototype.jochem.triptop.service;

import org.springframework.stereotype.Service;
import prototype.jochem.triptop.domain.Journey;
import prototype.jochem.triptop.domain.Transport;
import prototype.jochem.triptop.strategy.JourneyStrategy;

import java.util.List;

@Service
public class JourneyServiceImpl implements JourneyService {
    JourneyStrategy journeyStrategy;

    @Override
    public void setJourneyStrategy(JourneyStrategy journeyStrategy) {
        this.journeyStrategy = journeyStrategy;
    }

    @Override
    public List<Journey> getJourneys(String origin, String destination, String departureDate, String returnDate, double price, Transport transport) {
        return List.of();
    }
}
