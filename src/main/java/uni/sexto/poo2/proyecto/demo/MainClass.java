package uni.sexto.poo2.proyecto.demo;
import java.io.IOException;
import uni.sexto.poo2.proyecto.demo.ReadResources;
import uni.sexto.poo2.proyecto.demo.WebDownloader;

import java.util.*;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;



public class MainClass {
    public static void main(String[] args) throws IOException {
        HashMap<Integer, String> URLHashMap = ReadResources.ReadFileToMap("demo/src/main/java/uni/sexto/poo2/proyecto/demo/", "urls.txt");
        System.out.println(URLHashMap + "\n");

        // Download path


        int i = 0;
        for (Integer key : URLHashMap.keySet()) {
            System.out.println ("Key " + key + " Value " + URLHashMap.get (key));

            // Set URL and OUTPUT PATH for the downloader
            String url = URLHashMap.get (key);
            String OUTPUT_PATH = "demo/src/main/java/uni/sexto/poo2/proyecto/demo/downloads/demo_" + i;

            WebDownloader.downloadWithHttpClientAsync(url, OUTPUT_PATH);

            i++;
        }
    }
}