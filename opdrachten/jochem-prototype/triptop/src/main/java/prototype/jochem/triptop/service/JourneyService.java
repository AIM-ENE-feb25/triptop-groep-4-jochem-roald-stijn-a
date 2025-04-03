package prototype.jochem.triptop.service;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.stereotype.Service;
import prototype.jochem.triptop.domain.Journey;
import prototype.jochem.triptop.domain.Transport;

import java.util.List;

@Service
public interface JourneyService {
    void setJourneyStrategy(String journeyStrategy);
    List<Journey> getJourneys(String origin, String destination, String departureDate, String returnDate, double price, Transport transport) throws UnirestException;
}
