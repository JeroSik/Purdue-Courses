import java.io.*;
import java.util.*;

public class Solution {
    static void findRelevance(String[][] a, String max) {
        for (int i = 0; i < 10; i++) {
            if (a[i][1].equals(max)) {
                System.out.println(a[i][0]);
            }
        }
    }

    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        Scanner scan = new Scanner(System.in);
        int testCases = scan.nextInt();
        String[][] a = new String[10][2];
        int max;
        int temp;
        
        for (int i = 1; i <= testCases; i++) {
            max = 0;
            System.out.println("Case #" + i + ":");
            for (int j = 0; j < 10; j++) {
                a[j][0] = scan.next();
                temp = scan.nextInt();
                a[j][1] = Integer.toString(temp);
                if (temp > max) {
                    max = temp;
                }
            }
            findRelevance(a, Integer.toString(max));
        }
    }
}
