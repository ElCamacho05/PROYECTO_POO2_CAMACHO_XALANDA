package uni.sexto.poo2.proyecto.demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayDeque;
import java.util.Deque;

public class ReadResources {

    public static Deque<String> ReadFileToDeque (String pathname, String filename) throws FileNotFoundException, IOException {

        //String separator = "Data" + File.separator + "Stations" + File.separator + filename;
        //System.out.println(separator);

        // File reception
        File file = new File (pathname + filename);
        FileReader fileReader = new FileReader (file);

        BufferedReader buff = new BufferedReader (fileReader);

        String line;

        // Collection deque for returning urls
        Deque<String> deque = new ArrayDeque<>();

        // Cicling the urls and appending to deque
        while ((line = buff.readLine ()) != null) {
            deque.offerLast(line);
        }

        // Printing of all the lines


        // File safe closing
        buff.close ();
        fileReader.close ();
        return deque;
    }
}