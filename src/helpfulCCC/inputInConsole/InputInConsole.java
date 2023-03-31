package helpfulCCC.inputInConsole;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Created: 28.03.2023
 *
 * @author: Hannes Koppensteiner (Hannes)
 */

public class InputInConsole {
    private static final Scanner scan = new Scanner(System.in);

    private InputInConsole(){
    }

    public static String InputString(String msg) {
        System.out.print(msg);
        return scan.nextLine();
    }

    public static ArrayList<String> InputStringArray(String msg) {
        System.out.print(msg);
        ArrayList<String> arr =  new ArrayList<>();
        while(scan.hasNextLine()){
            System.out.print(msg);
            arr.add(scan.next());
        }
        return arr;
    }

    public static ArrayList<Integer> InputIntArray(String msg) {
        System.out.print(msg);
        Pattern pattern = Pattern.compile("\\d");
        ArrayList<Integer> arr = new ArrayList<>();

        while(scan.hasNext(pattern)){
            System.out.print(msg);
            arr.add(scan.nextInt());
        }
        return arr;
    }

    public static List<Double> InputDoubleArray(String msg) {
        ArrayList<Double> arr = new ArrayList<>();
        String lastMsg = "";
        while(true) {
            System.out.print(msg);
            if ((lastMsg = scan.nextLine()).equals("exit")) {
                break;
            }
            arr.add(Double.parseDouble(lastMsg));
        }
        return arr;
    }

}
