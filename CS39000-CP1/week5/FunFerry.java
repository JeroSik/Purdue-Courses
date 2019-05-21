import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {
    public static int maxCars(int ferry1, int ferry2, int[] cars) {
        // Track if full
        boolean full = false;
        int max = 0;
        
        for (int car : cars) {
                if (ferry1 - car >= 0) {
                    ferry1 -= car;
                    max++;
                } else if (ferry2 - car >= 0) {
                    ferry2 -= car;
                    max++;
                } else {
                    return max;
                }
        }
        
        return max;
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        // Initialize both lanes
        int ferry1 = scan.nextInt();
        int ferry2 = ferry1;
        
        // Initilize car query
        int num = scan.nextInt();
        int[] cars = new int[num];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = scan.nextInt();
        }
        
        System.out.println(maxCars(ferry1, ferry2, cars));
    }
}
