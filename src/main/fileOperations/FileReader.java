package main.fileOperations;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class FileReader {
    public static String ReadTextFile(String fileName) {
        StringBuilder data = new StringBuilder();

        try {
            File jsonFile = new File(fileName);
            Scanner reader = new Scanner(jsonFile);


            while (reader.hasNextLine()) {
                data.append(reader.nextLine());
            }

            reader.close();
        } catch (FileNotFoundException  e) {
            e.printStackTrace();
        }

        return data.toString();
    }

    public static ArrayList<String> GetAllFilesInFolder(String folderPath) {
        ArrayList<String> files = new ArrayList<>();

        File folder = new File(folderPath);
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < Objects.requireNonNull(listOfFiles).length; i++) {
          if (listOfFiles[i].isFile()) {
            files.add(listOfFiles[i].getName());
          }
        }

        return files;
    }
}
