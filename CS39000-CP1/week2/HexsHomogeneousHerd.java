import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {
    public static int findIndex(String[] input, int index) {
        for (int i = 0; i < input.length; i++) {
            if (!input[i].equals("-")) {
                if (Integer.parseInt(input[i]) == index) {
                    return i;
                }
            }
        }
        return -1;
    }
    
    public static int findCommon(int index1, int index2) {
        while (index1 != 0 || index2 != 0) {
            if (index1 == index2) {
                return index1;
            } else if (index1 > index2){
                if (index1 % 2 == 1) {
                    index1 = (index1 - 1) / 2;
                } else {
                    index1 = (index1 - 2) / 2;
                }
            } else {
                if (index2 % 2 == 1) {
                    index2 = (index2 - 1) / 2;
                } else {
                    index2 = (index2 - 2) / 2;
                }
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String[] input = scan.nextLine().split("\\s+");
        
        int n = scan.nextInt();
        int cow1, cow2;
        int index1, index2, youngest;
        for (int i = 0; i < n; i++) {
            cow1 = scan.nextInt();
            cow2 = scan.nextInt();
            index1 = findIndex(input, cow1);
            index2 = findIndex(input, cow2);
            if (index1 == -1 || index2 == -1) {
                System.out.println(-1);
                continue;
            }
            if (cow1 == cow2) {
                System.out.println(cow1);
                continue;
            }
            
            System.out.println(input[findCommon(index1, index2)]);
        }
    }
}
