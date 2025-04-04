package com.example.demo;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;

public class TripAdvisorAdapter implements IBookingAdapter {

    @Override
    public Hotel[] searchHotels(String location, String checkInDate, String checkOutDate) {

        int geoId = 60763; //TODO: get geoId from location search

        String url = "https://tripadvisor16.p.rapidapi.com/api/v1/hotels/searchHotels?geoId=" + geoId + "&checkIn=" + checkInDate + "&checkOut=" + checkOutDate + "&pageNumber=1&currencyCode=USD";
        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = null;
        try {
            response = Unirest.get(url)
                    .header("x-rapidapi-host", "tripadvisor16.p.rapidapi.com")
                    .header("x-rapidapi-key", "c2a11c5dc5mshc8163f22cd6e049p1b5d09jsn77de5b5baf7c")
                    .asString();
        } catch (UnirestException ignored) {
        }
        JSONObject jsonBody = new JSONObject(response.getBody());
        if (jsonBody.optJSONObject("data") == null) {
            return new Hotel[0];
        }
        JSONArray APIHotelData = jsonBody.getJSONObject("data").getJSONArray("data");

        Hotel[] hotels = new Hotel[APIHotelData.length()];
        for (int i = 0; i < APIHotelData.length(); i++) {
            JSONObject hotelData = APIHotelData.getJSONObject(i);
            hotels[i] = new Hotel();
            hotels[i].setName(hotelData.getString("title"));
            hotels[i].setDescription(hotelData.optString("primaryInfo"));
            hotels[i].setPrice(hotelData.optString("priceDetails"));
            hotels[i].setRating(hotelData.getJSONObject("bubbleRating").optDouble("rating") + "");
        }
        //TODO: get hotel details based on hotel ID

        return hotels;
    }
}
