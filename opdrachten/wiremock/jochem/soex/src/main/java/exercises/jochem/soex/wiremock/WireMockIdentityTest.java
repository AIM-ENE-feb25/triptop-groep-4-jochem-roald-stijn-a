package exercises.jochem.soex.wiremock;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;

public class WireMockIdentityTest {
    public static void main(String[] args) throws UnirestException {
        login("edevries", "3g2Rw9sT1x", "triptop");
        login("jkoning", "9wE3rT1y6u", "triptop");
        login("ldgr", "1xZ3cV5b7n", "triptop");
    }

    public static void login(String username, String password, String application) throws UnirestException {
        // JSON body met username en password
        JSONObject requestBody1 = new JSONObject();
        requestBody1.put("username", username);
        requestBody1.put("password", password);

        // Stuur een POST-request naar de WireMock IdentityProvider API
        HttpResponse<String> response1 = Unirest.post("https://triptop-identity.wiremockapi.cloud/login")
                .header("Content-Type", "application/json")
                .body(requestBody1.toString())
                .asString();

        // Print de response (bijvoorbeeld: voornaam, achternaam en token)
        System.out.println("Response code: " + response1.getStatus());
        System.out.println("Response body: " + response1.getBody());

        JSONObject responseBody = new JSONObject(response1.getBody());

        String tokenValue = responseBody.getJSONObject("token").getString("value");

        JSONObject requestBody2 = new JSONObject();
        requestBody2.put("username", username);
        requestBody2.put("application", application);

        // Stuur een POST-request naar de WireMock IdentityProvider API
        HttpResponse<String> response2 = Unirest.post("https://triptop-identity.wiremockapi.cloud/checkAppAccess?token=" + tokenValue)
                .header("Content-Type", "application/json")
                .body(requestBody2.toString())
                .asString();

        System.out.println("Response code: " + response2.getStatus());
        System.out.println("Response body: " + response2.getBody());
    }
}
