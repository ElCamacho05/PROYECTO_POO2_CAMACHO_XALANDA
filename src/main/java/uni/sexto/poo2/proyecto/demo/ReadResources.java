package uni.sexto.poo2.proyecto.demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.*;

public class ReadResources {

    public static HashMap<Integer, String> ReadFileToMap (String pathname, String filename) throws FileNotFoundException, IOException {

        // File reception
        File file = new File (pathname + filename);
        FileReader fileReader = new FileReader (file);

        BufferedReader buff = new BufferedReader (fileReader);

        String line;

        // Collection HashMap for returning urls
        HashMap<Integer, String> URLmap = new HashMap<> ();

        // Cicling the urls and adding into HashMap

        int i = 0;
        while ((line = buff.readLine ()) != null) {
            URLmap.put(i, line);
            i++;
        }

        // File safe closing
        buff.close ();
        fileReader.close ();
        return URLmap;
    }
}