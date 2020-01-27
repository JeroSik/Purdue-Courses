import java.util.*;
import java.lang.*;

public class LIS {
    public static void findLIS(int[] nums, int n) {
        int[] opt = new int[n];
        opt[0] = 1;
        int maxLIS = 0;

        for (int i = 1; i < n; i++) {
            opt[i] = 0;

            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    opt[i] = Math.max(opt[i], opt[j]);
                }
            }
            opt[i] = opt[i] + 1;
            maxLIS = Math.max(maxLIS, opt[i]);
        }

        System.out.println(maxLIS);
        backtrackLIS(opt, n);
    }

    public static void backtrackLIS(int[] opt, int n) {
        int max = 1;
        for (int i = 0; i < n; i++) {
            if (opt[i] == max) {
                System.out.print(i + " ");
                max++;
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        List<Integer> ans = new ArrayList<Integer>();

        while (scan.hasNextLine()) {
            int n = scan.nextInt();

            int[] nums = new int[n];
            for (int i = 0; i < n; i++) {
                nums[i] = scan.nextInt();
            }
                        scan.nextLine();

            findLIS(nums, n);
        }    
    }
}