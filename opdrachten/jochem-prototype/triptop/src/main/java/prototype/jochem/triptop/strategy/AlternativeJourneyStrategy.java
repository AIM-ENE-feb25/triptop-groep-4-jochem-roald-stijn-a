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
public class AlternativeJourneyStrategy implements JourneyStrategy {
    ExternalAPIHandlerImpl externalAPIHandler = new ExternalAPIHandlerImpl();

    @Override
    public List<Journey> getJourneys(String origin, String destination, String departureDate, String returnDate, double price, Transport transport) throws UnirestException {
        List<Journey> journeys = new ArrayList<>();

        getJourneysFromTransportType(externalAPIHandler.getTaxiTrips(origin), Transport.TAXI, journeys);
        getJourneysFromTransportType(externalAPIHandler.getFlightTrips(origin), Transport.VLIEGTUIG, journeys);
        getJourneysFromTransportType(externalAPIHandler.getCarRentalTrips(origin), Transport.HUURAUTO, journeys);

        return journeys;
    }

    public void getJourneysFromTransportType(String response, Transport transport, List<Journey> journeys) {
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
            journey.setTransport(transport);

            journeys.add(journey);
        }
    }
}