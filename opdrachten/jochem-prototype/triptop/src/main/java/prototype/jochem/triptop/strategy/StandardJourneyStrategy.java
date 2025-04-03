package prototype.jochem.triptop.strategy;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import prototype.jochem.triptop.domain.Journey;
import prototype.jochem.triptop.domain.Transport;
import prototype.jochem.triptop.repository.ExternalAPIHandlerImpl;

import java.util.ArrayList;
import java.util.List;

@Component
public class StandardJourneyStrategy implements JourneyStrategy {
    ExternalAPIHandlerImpl externalAPIHandler = new ExternalAPIHandlerImpl();

    @Override
    public List<Journey> getJourneys(String origin, String destination, String departureDate, String returnDate, double price, Transport transport) throws UnirestException {
        List<Journey> journeys = new ArrayList<>();
        String response = externalAPIHandler.getTaxiTrips(origin);

        JSONObject jsonResponse = new JSONObject(response);
        JSONArray tripsArray = jsonResponse.getJSONArray("data");

        for (int i = 0; i < tripsArray.length(); i++) {
            JSONObject tripObject = tripsArray.getJSONObject(i);
            Journey journey = new Journey();

            journey.setOrigin(tripObject.getString("name"));
            journey.setDestination(null);
            journey.setDepartureDate(null);
            journey.setReturnDate(null);
            journey.setPrice(0.0);
            journey.setTransport(Transport.TAXI);

            journeys.add(journey);
        }
        return journeys;
    }
}
