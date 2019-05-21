import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {
    public static boolean isValidOrder(int[] prices, int nuggets) {
        int pointer = prices.length - 1;
        while (pointer >= 0) {           
            if (nuggets - prices[pointer] >= 0) {
                nuggets -= prices[pointer];
            } else {
                pointer--;
            }
        } 
        if (nuggets == 0) {
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String str = scan.nextLine();
        String[] input = str.split("\\s+");
        int[] prices = new int[input.length];
        for (int i = 0; i < input.length; i++) {
            prices[i] = Integer.parseInt(input[i]);
        }
        
        int n = scan.nextInt();
        for (int i = 0; i < n; i++) {
            if (isValidOrder(prices, scan.nextInt())) {
                System.out.println("Big McThankies from McSpanky's");
            } else {
                System.out.println("Dude, it's salt");                
            }
        }
    }
}
