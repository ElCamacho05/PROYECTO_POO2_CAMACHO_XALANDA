package uni.sexto.poo2.proyecto.demo;
import java.io.IOException;
import uni.sexto.poo2.proyecto.demo.ReadResources;

import java.util.ArrayDeque;
import java.util.Deque;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;



public class MainClass {
    public static void main(String[] args) throws IOException {
        Deque<String> a = ReadResources.ReadFileToDeque("demo/src/main/java/uni/sexto/poo2/proyecto/demo/", "urls.txt");
        System.out.println(a);
        for (String line : a) {
            String url = line;
            System.out.println(url);
        }
    }


}