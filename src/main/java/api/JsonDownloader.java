package api;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;

public class JsonDownloader {

    private static final String API_URL = "https://apisinergial.imperdielriocuarto.com.ar/api/v3/Articulo/Web?PageNumber=1&PageSize=25000&ConStock=S&IncluyeSubFamilia=S&SoloConAplic=N&IdPlataforma=2&IdCliente=40&Orden=6&OrdenAscDesc=desc&IdSucursal=1&IdsMarcas=&IdFamilia=-1&PrecioDesde=&PrecioHasta=&IdMarcaAplicacion=-1&NroPieza=&IdsModelosAplic=&IdRubro=-1&CodigoNombreDescripcion=&Nombre=";

    private final HttpClient httpClient;
    private final AuthenticationService authService;

    public JsonDownloader() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(30))
                .build();
        this.authService = new AuthenticationService();
    }

    public void downloadFile() throws IOException, InterruptedException {
        String token = authService.getToken();
        String jsonResponse = makeRequest(API_URL, token);
        saveToFile(jsonResponse, "Articulos.json");
    }

    private String makeRequest(String url, String token) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(120))
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() >= 200 && response.statusCode() < 300) {
            return response.body();
        } else {
            throw new RuntimeException("Request failed with status: " + response.statusCode()
                    + " - " + response.body());
        }
    }

    private void saveToFile(String jsonContent, String filename) throws IOException {
        Path resourcesPath = Paths.get("src/main/resources");
        Path filePath = resourcesPath.resolve(filename);
        Files.write(filePath, jsonContent.getBytes());
    }
}
