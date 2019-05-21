import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {
    public static void findX(int p, int q, int r, int s, int t, int u) {
        double low = p + r + u;
        double high = p*Math.exp(-1) + q*Math.sin(1) + r*Math.cos(1) + s*Math.tan(1) + t + u;
        if (low < 0.0001 && low > 0.0001) {
            System.out.printf("%.4f\n", low);
            return;
        } else if (high < 0.0001 && high > 0.0001) {
            System.out.printf("%.4f\n", high);
            return;
        }
        
        // Check if both positive
        if (low > 0 && high > 0) {
            System.out.println("No solution");
            return;
        // Check if both negative
        } else if (low < 0 && high < 0) {
            System.out.println("No solution");
            return;
        }
        
        double delta = 0.0002;
        double y;
        for (double x = 0; x <= 1; x += 0.0001) {
            y = (p*Math.exp(-1*x)) + (q*Math.sin(x)) + (r*Math.cos(x)) + (s*Math.tan(x)) + (t*Math.pow(x, 2)) + u;
            if (y < delta && y > -1 * delta) {
                System.out.printf("%.4f\n", x);
                return;
            }
        }
        System.out.println("No solution");
    }
    
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        
        int p, q, r, s, t, u;
        while (scan.hasNextLine()) {
            p = scan.nextInt();
            q = scan.nextInt();
            r = scan.nextInt();
            s = scan.nextInt();
            t = scan.nextInt();
            u = scan.nextInt();
            findX(p, q, r, s, t, u);
        }
    }
}
