import java.util.ArrayList;
import java.util.List;

public class Fibonacci {

    public static void main(String[] args) {
        System.out.println(sFibonacci(38));
    }

    public static int sFibonacci(int n){
        List<Integer> arr = new ArrayList<>();
        arr.add(1);
        arr.add(1);

        if(n < 0){
            throw new IllegalArgumentException();
        }

        if(n == 1 || n == 2){
            return 1;
        }

        for (int i = 2; i < n; i++) {
            arr.add(arr.get(i-2) + arr.get(i-1));
        }
        return arr.get(arr.size()-1);
    }
}
