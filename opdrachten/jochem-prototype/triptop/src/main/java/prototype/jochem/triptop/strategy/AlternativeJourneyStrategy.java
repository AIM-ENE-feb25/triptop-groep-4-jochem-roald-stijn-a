package prototype.jochem.triptop.strategy;

import org.springframework.stereotype.Component;
import prototype.jochem.triptop.domain.Journey;
import prototype.jochem.triptop.domain.Transport;

import java.util.List;

@Component
public class AlternativeJourneyStrategy implements JourneyStrategy {
    @Override
    public List<Journey> getJourneys(String origin, String destination, String departureDate, String returnDate, double price, Transport transport) {
        return List.of();
    }
}
