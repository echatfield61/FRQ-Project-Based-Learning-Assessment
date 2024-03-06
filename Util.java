package dining_management;
import java.util.*;
import java.lang.Math;

public class Util {
    private static final Scanner scanner = new Scanner(System.in);

    public static int getInt() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            return 0;
        }
    }

    public static double sigmoid(double value){
        return 1 / (1 + Math.pow(2.814, -1*value));
    }
}
