package CCC.honeycomb;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Main {

    public static int countWasps(String filename){
        List<List<String>> list;
        list = readFromCSV(filename);
        //System.out.println(list);

        list = list.stream().map(lst -> lst.stream().filter(el -> Objects.equals(el, "O")).toList()).toList();
        //System.out.println(list);
        int anz = 0;

        for (List<String> lst : list) {
            anz += lst.size();
        }
        return anz;
    }

    public static int countNeighboringEmptyCells(List<List<String>>list){
        //System.out.println(list);
        int[] wasp = findWasp(list);

        System.out.println(Arrays.toString(wasp));
        int anz = 0;

        if (list.get(wasp[0]).get(0).equals("")) {
            // line above and below
            System.out.println(list);
            List<String> lineAbove = list.get(wasp[0]-1);
            List<String> lineBelow = list.get(wasp[0]+1);
            //System.out.println("Line above1: " + lineAbove);
            //System.out.println("Line below1" + lineBelow);

            if (lineAbove.get(wasp[1]-1).equals("O")){
                anz++;
            }
            if (lineAbove.get(wasp[1]).equals("O")) {
                anz++;
            }
            if (lineBelow.get(wasp[1]-1).equals("O")){
                anz++;
            }
            if (lineBelow.get(wasp[1]).equals("O")) {
                anz++;
            }
        } else {
            System.out.println(list);
            List<String> lineAbove = list.get(wasp[0]-1);
            List<String> lineBelow = list.get(wasp[0]+1);
            //System.out.println("Line above2: " + lineAbove);
            //System.out.println("Line below2" + lineBelow);
            if (lineAbove.get(wasp[1]).equals("O")){
                anz++;
            }
            if (lineAbove.get(wasp[1]+1).equals("O")) {
                anz++;
            }
            if (lineBelow.get(wasp[1]).equals("O")){
                anz++;
            }
            if (lineBelow.get(wasp[1]+1).equals("O")) {
                anz++;
            }
        }

        if (list.get(wasp[0]).get(wasp[1]-1).equals("O")) {
            anz++;
        }
        if (list.get(wasp[0]).get(wasp[1]+1).equals("O")) {
            anz++;
        }

        return anz;
    }

    public static List<Integer> getAnzahl(List<List<List<String>>> lst) {
        List<Integer> count = new ArrayList<>();

        for (List<List<String>> aufbau : lst) {
            count.add(countNeighboringEmptyCells(aufbau));
        }
        return count;
    }

    // readFromFile - Level 2:
    public static List<List<List<String>>> readFromFile(String filename) {
        List<List<List<String>>> list = new ArrayList<>();
        int anz;

        try (BufferedReader br = Files.newBufferedReader(Path.of(filename))) {
            anz = Integer.parseInt(br.readLine());
            //System.out.println(anz);
            br.readLine();

            for (int i = 0; i < anz; i++) {
                List<List<String>> aufbau = new ArrayList<>();
                boolean aufbauEinlesen = true;
                String line = "";

                while (aufbauEinlesen) {
                    line = br.readLine();

                    if (line == null) {
                        break;
                    }

                    if (line.isBlank()) {
                        //System.out.println(aufbau);
                        aufbauEinlesen = false;
                    } else {
                        aufbau.add(readCSVString(line));
                    }
                }
                list.add(aufbau);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static boolean isFree(List<List<String>> aufbau) {
        int[] wasp = findWasp(aufbau);

        //x - Zeile | y - Index

        try {
            for (int x = -1; x < 1; x++) {
                for(int y = -1; y < 2; y=y+1) {

                    if (x==0) {
                        break;
                    }
                    int currentX = wasp[0];
                    int currentY = wasp[1];

                    boolean thisDirection = true;

                    while (thisDirection) {
                        currentX += x;
                        currentY += y;
                        System.out.println("x: " + x + " y: " + y);
                        String zelle = aufbau.get(currentX).get(currentY);
                        System.out.println(zelle);
                        if (zelle.equals("X")) {
                            thisDirection = false;
                            break;
                        }
                        if (zelle.equals("")) {
                            return true;
                        }
                    }
                }
            }
        } catch (IndexOutOfBoundsException e) {
            return true;
        }
        return false;
    }

    public static int[] findWasp (List<List<String>> list){
        int[] wasp = new int[2];

        for (int i = 0; i < list.size(); i++) {
            //System.out.println(list.get(i));
            int indexInLine  = list.get(i).indexOf("W");
            if (indexInLine != -1) {
                wasp[0] = i;
                wasp[1] = indexInLine;
                break;
            }
        }
        return wasp;
    }

    public static List<String> getIsFreeOutput(List<List<List<String>>> lst) {
        List<String> isFreeOrNot = new ArrayList<>();

        for (List<List<String>> aufbau : lst) {
            isFreeOrNot.add(isFree(aufbau) ? "FREE" : "TRAPPED");
        }
        return isFreeOrNot;
    }

    // readFromFile - Level 1:
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

    // A
    public static List<String> readCSVString(String s) {
        String[] splitted = s.split("-");
        return Arrays.stream(splitted).toList();
    }

    public static void main(String[] args) {
        for (int i = 1; i <= 5; i++) {
            String filename = "./resources/level3/level3_" + i + ".in";
            String filenameOutput = "./resources/level3/level3Output-" + i + ".txt";

            try (BufferedWriter bf = Files.newBufferedWriter(Path.of(filenameOutput))) {
                for (String s : getIsFreeOutput(readFromFile(filename))) {
                    bf.write(s + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}