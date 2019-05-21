import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {
    public static int allSubSum(int hit, int[] cards) {
        int total = 0;
        int sum;
        for (int i = 0; i < cards.length; i++) { 
            for (int j = i; j < cards.length; j++) { 
                sum = 0;
                for (int k = i; k <= j; k++) {
                    sum += cards[k]; 
                }
                if (sum == hit) {
                    total++;
                }
            } 
        } 
        return total; 
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int hit = scan.nextInt();
        int n = scan.nextInt();
        int[] cards = new int[n];
        
        for (int i = 0; i < n; i++) {
            cards[i] = scan.nextInt();
        }
        
        System.out.println(allSubSum(hit, cards));
    }
}
