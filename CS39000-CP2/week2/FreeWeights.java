import java.util.*;
import java.lang.*;

public class FreeWeights {
    public static int findMinimumWeight(int[][] weights, int low, int high) {
        if (low == high) {
            return low;
        } else if (low + 1 == high) {
            return low;
            // return pos(low) ? low : high;
        }

        int mid = (low + high) / 2;
        if (okay(mid)) {
            return findMinimumWeight(low, mid);
        } else {
            return findMinimumWeight(mid + 1, high);
        }
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int n = scan.nextInt();
        int[][] weights = new int[2][n];
        int low = Integer.MAX_VALUE;
        int high = 1;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < n; j++) {
                weights[i][j] = scan.nextInt();
                low = Math.min(low, weights[i][j]);
                high = Math.max(high, weights[i][j]);
            }
        }

        System.out.println(findMinimumWeight(weights, low, high));
    }
}