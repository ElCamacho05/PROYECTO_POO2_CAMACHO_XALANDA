package uni.sexto.poo2.proyecto.demo;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WebDownloader {

    public static void downloadWithHttpClientAsync(String url, String outputPathWithoutExt) {
        HttpClient client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.ALWAYS) // URLs from picsum always redirect
                .build();

        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .GET()
                .build();

        // Code to excecute when client starts
        client.sendAsync(request, HttpResponse.BodyHandlers.ofByteArray())
                .thenAccept(response -> {
                    String contentType = response.headers()
                            .firstValue("Content-Type")
                            .orElse("image/jpeg");

                    // Switch all the possible file types
                    String extension = ".img";
                    if (contentType.contains("png")) {
                        extension = ".png";
                    } else if (contentType.contains("jpeg") || contentType.contains("jpg")) {
                        extension = ".jpg";
                    }
                    String finalPath = outputPathWithoutExt + extension;

                    try (FileOutputStream fos = new FileOutputStream(finalPath)) {
                        fos.write(response.body());
                        System.out.println("Saved as: " + finalPath + "\n");
                    } catch (IOException e) {
                        System.out.println("Failed download: " + e.getMessage() + "\n");
                    }
                })
                .exceptionally(ex -> {
                    System.out.println("Failed download: \n" + ex.getMessage() + "\n");
                    return null;
                });

        // Thread sleep (just in case...)


        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    // Test Class
    public static void main(String[] args) {
        String testUrl = "https://firebasestorage.googleapis.com/v0/b/departmentstorebackend.appspot.com/o/images%2F53405989488_380a600691.png?alt=media&token=f6eacae0-9a9b-4a96-97f8-093c8fc963fe";
        String output = "demo/src/main/java/uni/sexto/poo2/proyecto/demo/downloads/demo_image";

        downloadWithHttpClientAsync(testUrl, output);
    }
}
