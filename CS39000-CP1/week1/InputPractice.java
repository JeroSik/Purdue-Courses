import java.io.*;
import java.util.*;

public class Solution {

    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        int x, sum;
        for (int i = 0; i < n; i++) {
            sum = 0;
            x = scan.nextInt();
            for (int j = 0; j < x; j++) {
                sum += scan.nextInt();
            }
            System.out.println(sum);
        }
    }
}
