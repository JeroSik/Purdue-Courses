import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {
    public static int decoder(int[] nums) {
        int[] count = new int[nums.length + 1];
        // Base cases
        count[0] = 1;
        count[1] = 1;
        // Search through rest of numbers
        for (int i = 2; i <= nums.length; i++) {
            count[i] = 0;
            if (nums[i - 1] > 0) {
                count[i] = count[i - 1];
            }
            if (nums[i - 2] == 1 || (nums[i - 2] == 2 && nums[i - 1] <= 6)) {
                count[i] += count[i - 2];
            }
        } 
        return count[nums.length];
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String s = scan.nextLine();
        int[] nums = new int[s.length()];
        for (int i = 0; i < s.length(); i++) {
            nums[i] = Integer.parseInt(String.valueOf(s.charAt(i)));
        }
        System.out.println(decoder(nums));
    }
}
