package api;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class JsonDownloader {

    private static final String API_URL = "https://apisinergial.imperdielriocuarto.com.ar/api/v3/Articulo/Web?PageNumber=1&PageSize=25000&ConStock=S&IncluyeSubFamilia=S&SoloConAplic=N&IdPlataforma=2&IdCliente=40&Orden=6&OrdenAscDesc=desc&IdSucursal=1&IdsMarcas=&IdFamilia=-1&PrecioDesde=&PrecioHasta=&IdMarcaAplicacion=-1&NroPieza=&IdsModelosAplic=&IdRubro=-1&CodigoNombreDescripcion=&Nombre=";
    private static final int MAX_AGE_DAYS = 5;

    private final HttpClient httpClient;
    private final AuthenticationService authService;

    public JsonDownloader() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(30))
                .build();
        this.authService = new AuthenticationService();
    }

    public interface DownloadListener {
        void onDownloadStarted();
        void onDownloadComplete();
    }

    public void downloadFile() throws IOException, InterruptedException {
        downloadFile(null);
    }

    public void downloadFile(DownloadListener listener) throws IOException, InterruptedException {
        Path appDir = Paths.get(System.getProperty("user.home"), ".consultorimperdiel");
        Files.createDirectories(appDir);
        Path filePath = appDir.resolve("Articulos.json");

        if (shouldDownload(filePath)) {
            System.out.println("Downloading latest data from API...");
            if (listener != null) {
                listener.onDownloadStarted();
            }
            String token = authService.getToken();
            String jsonResponse = makeRequest(API_URL, token);
            saveToFile(jsonResponse, "Articulos.json");
            System.out.println("Download complete.");
            if (listener != null) {
                listener.onDownloadComplete();
            }
        } else {
            System.out.println("Using existing Articulos.json file (less than " + MAX_AGE_DAYS + " days old).");
        }
    }

    private boolean shouldDownload(Path filePath) throws IOException {
        File file = filePath.toFile();

        // Download if file doesn't exist
        if (!file.exists()) {
            return true;
        }

        // Download if file is older than MAX_AGE_DAYS
        FileTime lastModified = Files.getLastModifiedTime(filePath);
        Instant fileDate = lastModified.toInstant();
        Instant now = Instant.now();
        long daysBetween = ChronoUnit.DAYS.between(fileDate, now);

        return daysBetween >= MAX_AGE_DAYS;
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
        Path appDir = Paths.get(System.getProperty("user.home"), ".consultorimperdiel");
        Files.createDirectories(appDir);
        Path filePath = appDir.resolve(filename);
        Files.write(filePath, jsonContent.getBytes());
    }
}
