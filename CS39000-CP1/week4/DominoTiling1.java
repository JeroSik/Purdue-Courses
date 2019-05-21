import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {
    public static int tiling(int n, int[] normal, int[] off) {
        for (int i = 2; i <= n; i++) {
            normal[i] = normal[i-2] + 2 * off[i-1];
            off[i] = normal[i-1] + off[i-2];
        }
        return normal[n];
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        int[] normal;
        int[] off;
        while (n != -1) {
            if (n % 2 == 0 && n > 1) {
                normal = new int[n + 1];
                off = new int[n + 1];
                normal[0] = 1;
                normal[1] = 0;
                off[0] = 0;
                off[1] = 1;
                System.out.println(tiling(n, normal, off));
            } else if (n == 0) {
                System.out.println(1);
            } else {
                System.out.println(0);
            }
            n = scan.nextInt();
        }
    }
}
