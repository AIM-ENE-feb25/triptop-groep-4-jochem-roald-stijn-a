package com.example.demo;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) throws UnirestException {
		String from = "Schiphol";
		String to = "Barcelona";
		String departDate = "2025-03-22";
		String returnDate = "2025-03-29";

		HttpResponse<String> fromIdResponse = Unirest.get("https://booking-com15.p.rapidapi.com/api/v1/flights/searchDestination?query="+from)
				.header("x-rapidapi-key", "92b62a4079mshe417d603a8d8c05p1bbb0fjsn42969109f91c")
				.header("x-rapidapi-host", "booking-com15.p.rapidapi.com")
				.asString();

		JSONObject fromIdResponseBody = new JSONObject(fromIdResponse.getBody());
		String fromId = fromIdResponseBody.getJSONArray("data").getJSONObject(0).getString("id");

		HttpResponse<String> toIdResponse = Unirest.get("https://booking-com15.p.rapidapi.com/api/v1/flights/searchDestination?query="+to)
				.header("x-rapidapi-key", "92b62a4079mshe417d603a8d8c05p1bbb0fjsn42969109f91c")
				.header("x-rapidapi-host", "booking-com15.p.rapidapi.com")
				.asString();

		JSONObject toIdResponseBody = new JSONObject(toIdResponse.getBody());
		String toId = toIdResponseBody.getJSONArray("data").getJSONObject(0).getString("id");

		HttpResponse<String> response = Unirest.get("https://booking-com15.p.rapidapi.com/api/v1/flights/searchFlights?fromId=" + fromId + "&toId=" + toId + "&departDate=" + departDate + "&returnDate=" + returnDate + "&pageNo=1&adults=1&sort=BEST&cabinClass=ECONOMY&currency_code=EUR")
				.header("x-rapidapi-key", "92b62a4079mshe417d603a8d8c05p1bbb0fjsn42969109f91c")
				.header("x-rapidapi-host", "booking-com15.p.rapidapi.com")
				.asString();

		JSONObject responseBody = new JSONObject(response.getBody());
		// response.data.aggregation.minPrice.units (hele euros, int) en .nanos (centen, int)
		JSONObject minPriceInfo = responseBody.getJSONObject("data").getJSONObject("aggregation").getJSONObject("minPrice");
		double minPrice = minPriceInfo.getInt("units") + minPriceInfo.getInt("nanos") / 100.0;

		System.out.println("The cheapest flight from " + from + " to " + to + " on " + departDate + " and back on " + returnDate + " costs " + minPrice + " euros.");

	}

}
