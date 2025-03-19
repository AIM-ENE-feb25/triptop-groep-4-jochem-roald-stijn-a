package com.example.demo;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) throws UnirestException {
		DemoApplication app = new DemoApplication();
		app.sendLoginRequest("edevries", "3g2Rw9sT1x");
		app.sendLoginRequest("fvleeuwen", "0qW3rE5t7y");
		app.sendLoginRequest("mvdlinden", "1xZ3cV5b7n");
	}

	private void sendLoginRequest(String username, String password) throws UnirestException {
		// System.out.println("Sending login request to WireMock API...");
		JSONObject loginRequestBody = new JSONObject();
		loginRequestBody.put("username", username);
		loginRequestBody.put("password", password);

		HttpResponse<String> loginResponse = Unirest.post("https://triptop-identity.wiremockapi.cloud/login")
				.header("Content-Type", "application/json")
				.body(loginRequestBody.toString())
				.asString();

		// System.out.println("Status code: " + loginResponse.getStatus());
		if (loginResponse.getStatus() < 200 || loginResponse.getStatus() >= 300) {
			System.out.println("Response body: " + loginResponse.getBody());
			return;
		}

		JSONObject loginResponseBody = new JSONObject(loginResponse.getBody());
		String token = loginResponseBody.getJSONObject("token").getString("value");
		// System.out.println("Token: " + token);

		// System.out.println("Sending auth request to WireMock API with token...");
		JSONObject authRequestBody = new JSONObject();
		authRequestBody.put("username", username);
		authRequestBody.put("application", "triptop");

		HttpResponse<String> authResponse = Unirest.post("https://triptop-identity.wiremockapi.cloud/checkAppAccess?token=" + token)
				.header("Content-Type", "application/json")
				.body(authRequestBody.toString())
				.asString();

		if (authResponse.getStatus() < 200 || authResponse.getStatus() >= 300) {
			System.out.println("Response body: " + authResponse.getBody());
			return;
		}

		// System.out.println("Status code: " + authResponse.getStatus());
		System.out.println("Response body: " + authResponse.getBody());
	}

}
