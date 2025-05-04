package uni.sexto.poo2.proyecto.demo;

import java.io.IOException;
import java.io.File;

import uni.sexto.poo2.proyecto.demo.ReadResources;
import uni.sexto.poo2.proyecto.demo.WebDownloader;

import java.util.*;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.util.concurrent.*;

public class MainClass {

    // Executor definition with 5 elements in thread pool
    private static final ExecutorService processingExecutor = Executors.newFixedThreadPool(5);

    public static void main(String[] args) throws IOException {

        // Get HashMap from given text file
        HashMap<Integer, String> URLHashMap = ReadResources.ReadFileToMap("demo/src/main/java/uni/sexto/poo2/proyecto/demo/", "urls.txt");
        System.out.println(URLHashMap + "\n");


        int i = 0;

        for (Integer key : URLHashMap.keySet()) {
            final int i_f = i; // final int just for the thread
            processingExecutor.submit(() -> {
                System.out.println ("Key " + key + " Value " + URLHashMap.get (key) + ", will be downloaded via thread" + Thread.currentThread ().getName ());
                // Set URL and OUTPUT PATH for the downloader
                String url = URLHashMap.get (key);
                String OUTPUT_PATH = "demo/src/main/java/uni/sexto/poo2/proyecto/demo/downloads/image_" + i_f;

                // Download from the given url
                WebDownloader.downloadWithHttpClientAsync(url, OUTPUT_PATH);
            });
            i++;
        }


        processingExecutor.shutdown();
        try {
            if (!processingExecutor.awaitTermination(60, TimeUnit.SECONDS)) {
                processingExecutor.shutdownNow(); // Forzamos cierre si no terminó
            }
        } catch (InterruptedException e) {
            processingExecutor.shutdownNow();
            Thread.currentThread().interrupt();
        }

        // Thread sleep (just in case...)
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Call function for image threaded processing
        ImageProcessor.processImagesFromDirectory();

    }
}