package prototype.jochem.triptop.repository;

import com.mashape.unirest.http.exceptions.UnirestException;

public interface ExternalAPIHandler {
    String getTaxiTrips(String location) throws UnirestException;

    String getFlightTrips(String location) throws UnirestException;

    String getCarRentalTrips(String location) throws UnirestException;
}
