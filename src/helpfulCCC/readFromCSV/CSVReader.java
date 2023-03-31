package helpfulCCC.readFromCSV;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created: 28.03.2023
 *
 * @author: Hannes Koppensteiner (Hannes)
 */

public class CSVReader {

    public static List<List<String>> readFromCSV(String filename) {
        List<List<String>> list = new ArrayList<>();

        try (BufferedReader br = Files.newBufferedReader(Path.of(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                list.add(readCSVString(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<String> readCSVString(String s) {
        String[] splitted = s.split(";");
        return Arrays.stream(splitted).toList();
    }

    public static <T extends Number> boolean checkNumber(T number) {
        if (number.doubleValue() < 0.0) {
           return false;
        }
        return true;
    }

    public static long length(String filename) throws Exception, RuntimeException {
        File file = new File(filename);
        return file.length();
    }
}