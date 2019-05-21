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
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = scan.nextInt();
        }
        
        int index = 0;
        int profit = 0;
        int seller1;
        int seller2;
        int max = 0;
        int temp = 0;
        for (int i = 0; i < (n - 2) / 2; i++) {
            seller1 = arr[i] - arr[2 * i + 1];
            seller2 = arr[i] - arr[2 * i + 2];
            temp = Math.max(seller1, seller2);
            if (temp > max) {
                max = temp;
                index = i;
            }
        }
        System.out.println(index);
    }
}
