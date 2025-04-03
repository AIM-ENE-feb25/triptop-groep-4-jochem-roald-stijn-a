package com.example.demo;


import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;

public class BookingComAdapter implements IBookingAdapter {

    @Override
    public Hotel[] searchHotels(String location, String checkInDate, String checkOutDate) {

        int dest_id = -2092174; //TODO: get dest_id from location search

        String url = "https://booking-com15.p.rapidapi.com/api/v1/hotels/searchHotels?dest_id=" + dest_id + "&search_type=CITY&arrival_date=" + checkInDate + "&departure_date=" + checkOutDate + "&adults=1&children_age=0%2C17&room_qty=1&page_number=1&units=metric&temperature_unit=c&languagecode=en-us&currency_code=AED&location=US";
        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = null;
        try {
            response = Unirest.get(url)
                    .header("x-rapidapi-host", "booking-com15.p.rapidapi.com")
                    .header("x-rapidapi-key", "c2a11c5dc5mshc8163f22cd6e049p1b5d09jsn77de5b5baf7C")
                    .asString();
        } catch (UnirestException ignored) {
        }
        JSONObject jsonBody = new JSONObject(response.getBody());
        if (jsonBody.optJSONObject("data") == null) {
            return new Hotel[0];
        }
        JSONArray APIHotelData = jsonBody.getJSONObject("data").getJSONArray("hotels");

        Hotel[] hotels = new Hotel[APIHotelData.length()];
        for (int i = 0; i < APIHotelData.length(); i++) {
            JSONObject hotelData = APIHotelData.getJSONObject(i);
            hotels[i] = new Hotel();
            hotels[i].setName(hotelData.getJSONObject("property").getString("name"));
            hotels[i].setDescription(hotelData.optString("accessibilityLabel"));
            hotels[i].setPrice(hotelData.getJSONObject("property").getJSONObject("priceBreakdown").getJSONObject("grossPrice").optDouble("value") + " " + hotelData.getJSONObject("property").getJSONObject("priceBreakdown").getJSONObject("grossPrice").optString("currency"));
            hotels[i].setRating(hotelData.getJSONObject("property").optInt("reviewScore") + "");
        }
        //TODO: get hotel details based on hotel ID

        return hotels;
    }
}
