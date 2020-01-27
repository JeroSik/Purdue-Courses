import java.util.*;
import java.lang.*;

public class FreeWeights {
    public static int findMinimumWeight(int[][] weights, int low, int high, int n) {
        if (high - low <= 1) {
            return low;
        } 

        int mid = (low + high) / 2;
        if (checkWeight(weights, mid, n)) {
            return findMinimumWeight(weights, low, mid, n);
        } else {
            return findMinimumWeight(weights, mid, high, n);
        }
    }

    public static boolean checkWeight(int[][] weights, int mid, int n) {
        for (int row = 0; row < 2; row++) {
            int adj = 0;
            for (int i = 0; i < n; i++) {
                int curr = weights[row][i];

                if (curr >= mid) {
                    if (adj == 0) {
                        adj = curr;
                    } else {
                        if (adj != curr) {
                            return false;
                        } else {
                            adj = 0;
                        }
                    }
                }
            }

            if (adj != 0) {
                return false;
            }
        }

        return true;
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

        if (inOrder(weights, n)) {
            System.out.println(0);
        } else {
            System.out.println(findMinimumWeight(weights, low - 1, high + 1, n));
        }
    }

    public static boolean inOrder(int[][] weights, int n) {
        if (n % 2 == 1) {
            return false;
        }

        for (int row = 0; row < 2; row++) {
            for (int i = 0; i < n; i += 2) {
                if (weights[row][i] != weights[row][i + 1]) {
                    return false;
                }
            }
        }

        return true;
    }
}