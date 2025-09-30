package api;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

public class AuthenticationService {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public AuthenticationService() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(30))
                .build();
        this.objectMapper = new ObjectMapper();
    }

    public String getToken() throws IOException, InterruptedException {
        final String url = "https://apisinergial.imperdielriocuarto.com.ar/api/v1/Login";
        final Map<String, Object> body = new HashMap<>();
        body.put("usuario", "cod40");
        body.put("clave", "cod40");
        body.put("email", "");
        body.put("idPlataformaIntegracion", 2);

        HttpResponse<String> response = makeRequest(url, body);
        return cleanResponse(response);
    }

    private HttpResponse<String> makeRequest(String url, Map<String, Object> body) throws IOException, InterruptedException {
        String jsonBody = objectMapper.writeValueAsString(body);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(30))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private String cleanResponse(HttpResponse<String> response) throws IOException {
        if (response.statusCode() >= 200 && response.statusCode() < 300) {
            JsonNode jsonResponse = objectMapper.readTree(response.body());
            JsonNode dataNode = jsonResponse.get("Data");

            if (dataNode != null && !dataNode.isNull()) {
                JsonNode tokenNode = dataNode.get("Token");
                if (tokenNode != null && !tokenNode.isNull()) {
                    return tokenNode.asText();
                } else {
                    throw new RuntimeException("Token field not found in Data object");
                }
            } else {
                throw new RuntimeException("Data field not found in response");
            }
        } else {
            throw new RuntimeException("Authentication failed with status: " + response.statusCode()
                    + " - " + response.body());
        }
    }
}
