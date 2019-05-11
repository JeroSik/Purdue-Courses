import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {

    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        String[] query = new String[n];
        scan.nextLine();
        for (int i = 0; i < n; i++) {
            query[i] = scan.nextLine();
        }
        
        HashSet set = new HashSet();
        for (int i = 0; i < n; i++) {
            if (set.contains(query[i])) {
                System.out.println("IT'S BEEN DONE ALREADY");
            } else {
                System.out.println(query[i]);
                set.add(query[i]);
            }
        }
    }
}
