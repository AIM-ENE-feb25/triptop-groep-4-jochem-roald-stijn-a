package prototype.jochem.triptop.service;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.stereotype.Service;
import prototype.jochem.triptop.domain.Journey;
import prototype.jochem.triptop.domain.Transport;
import prototype.jochem.triptop.strategy.AlternativeJourneyStrategy;
import prototype.jochem.triptop.strategy.JourneyStrategy;
import prototype.jochem.triptop.strategy.StandardJourneyStrategy;

import java.util.List;

@Service
public class JourneyServiceImpl implements JourneyService {
    JourneyStrategy journeyStrategy;

    @Override
    public void setJourneyStrategy(String journeyStrategy) {
        switch (journeyStrategy) {
            case "StandardJourney":
                this.journeyStrategy = new StandardJourneyStrategy();
                break;
            case "AlternativeJourney":
                this.journeyStrategy = new AlternativeJourneyStrategy();
                break;
            default:
                throw new IllegalArgumentException("Invalid journey strategy: " + journeyStrategy);
        }
    }

    @Override
    public List<Journey> getJourneys(String origin, String destination, String departureDate, String returnDate, double price, Transport transport) throws UnirestException {
        return journeyStrategy.getJourneys(origin, destination, departureDate, returnDate, price, transport);
    }
}
