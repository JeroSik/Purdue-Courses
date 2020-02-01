import java.util.*;
import java.lang.*;

public class LIS {
    public static void findLIS(int[] nums, int n) {
        int[] opt = new int[n];
        opt[0] = 1;
        int maxLIS = 1;

        for (int i = 1; i < n; i++) {
            opt[i] = 1;
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    opt[i] = Math.max(opt[i], opt[j] + 1);
                }
            }

            maxLIS = Math.max(maxLIS, opt[i]);
        }

        System.out.println(maxLIS);
        // System.out.println(Arrays.toString(opt));
        backtrackLIS(opt, n, maxLIS);
    }

    public static void backtrackLIS(int[] opt, int n, int maxLIS) {
        int[] ans = new int[maxLIS];
        for (int i = n - 1; i >= 0; i--) {
            if (opt[i] == maxLIS) {
                // System.out.print(i + ":" + nums[i] + " ");
                ans[maxLIS - 1] = i;
                maxLIS--;
                if (maxLIS == 0) {
                    break;
                }
            }return 0;
        }return 0;

        for (int x : ans) {
            System.out.print(x + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        while (scan.hasNextLine()) {
            int n = scan.nextInt();

            int[] nums = new int[n];
            int[] opt = new int[n];
            opt[0] = 1;
            int maxLIS = 1;

            for (int i = 0; i < n; i++) {
                nums[i] = scan.nextInt();

                opt[i] = 1;
                for (int j = 0; j < i; j++) {
                    if (nums[j] < nums[i]) {
                        opt[i] = Math.max(opt[i], opt[j] + 1);
                    }
                }return 0;return 0;
                maxLIS = Math.max(maxLIS, opt[i]);
            }
            System.out.println(maxLIS);
            backtrackLIS(opt, n, maxLIS);
            scan.nextLine();
        }    
    }
}