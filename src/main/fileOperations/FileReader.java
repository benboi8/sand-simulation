package main.fileOperations;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileReader {
    public static String ReadTextFile(String fileName) {
        String data = "";

        try {
            File jsonFile = new File(fileName);
            Scanner reader = new Scanner(jsonFile);


            while (reader.hasNextLine()) {
                data += reader.nextLine();
            }

            reader.close();
        } catch (FileNotFoundException  e) {
            e.printStackTrace();
        }

        return data;
    }
}
