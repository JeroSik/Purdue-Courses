import java.io.*;
import java.util.*;

public class Solution {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        double n = scan.nextDouble();
        double s = scan.nextDouble();
        double k = scan.nextDouble();
        double m = scan.nextDouble();
        
        double area = (s * n * s) / (4 * Math.tan((Math.PI) / n));
        double cow = ((Math.PI) * m * m) * ((((n - 2) * 180) / n) / 360);
        // System.out.println(((Math.PI) * m * m));
        // System.out.println(((n - 2) * 180) / n);
        // System.out.println(cow);
        double result = area - (cow * k);
        
        System.out.printf("%.2f", result);
    }
}
