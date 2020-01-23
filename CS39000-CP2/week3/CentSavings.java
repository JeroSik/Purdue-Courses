import java.util.*;
import java.lang.*;

public class CentSavings {
    public static int roundTo10(int x) {
        return ((x + 5) / 10) * 10;
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int n = scan.nextInt();
        int d = scan.nextInt();

        int[] items = new int[n];
        int[] total = new int[n];
        int[][] opt = new int[n][d];
        for (int i = 0; i < n; i++) {
            items[i] = scan.nextInt();
            total[i] = i == 0 ? items[i] : total[i - 1] + items[i];
            
            for (int j = 0; j < d; j++) {
                opt[i][j] = roundTo10(total[i]);
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < d; j++) {
                for (int k = 0; k < i; k++) {
                    opt[i][j] = j == 0 ? opt[i][j] : Math.min(opt[i][j], opt[k][j - 1] + roundTo10(total[i] - total[k]));
                }
            }
        }

        System.out.println(opt[n - 1][d - 1]);
    }
}