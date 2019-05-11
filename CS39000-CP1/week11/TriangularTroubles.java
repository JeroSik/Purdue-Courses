import java.io.*;
import java.util.*;

public class Solution {

    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        Scanner scan = new Scanner(System.in);
        int t = scan.nextInt();
        
        for (int i = 0; i < t; i++) {
            int a = scan.nextInt();
            int b = scan.nextInt();
            int c = scan.nextInt();
            
            if (a == b && b == c) {
                System.out.println("B");
            } else if (a == b || a == c || b == c) {
                System.out.println("A");
            } else {
                System.out.println("C");
            }
        }
    }
}
