package prototype.jochem.triptop.repository;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class ExternalAPIHandlerImpl implements ExternalAPIHandler {
    @Override
    public String call(Endpoint endpoint) {
        return "";
    }

    public String getTaxiTrips(String location) throws UnirestException {
        HttpResponse<String> response = Unirest.get("https://booking-com15.p.rapidapi.com/api/v1/taxi/searchLocation?query=" + location)
                .header("x-rapidapi-key", "410bb84dccmshe1bf03f1e953c69p13989ejsna73c05ae6736")
                .header("x-rapidapi-host", "booking-com15.p.rapidapi.com")
                .asString();

        return response.getBody();
    }
}
