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
        int[] total = new int[n + 1];
        int[][] opt = new int[n + 1][d + 1];
        for (int i = 0; i < n; i++) {
            items[i] = scan.nextInt();
            total[i + 1] = total[i] + items[i];
            
            for (int j = 0; j <= d; j++) {
                opt[i + 1][j] = roundTo10(total[i + 1]);
            }
        }

        for (int j = 1; j <= d; j++) {
            for (int i = 1; i <= n; i++) {
                for (int k = 0; k < i; k++) {
                    opt[i][j] = k == 0 ? opt[k][j - 1] + roundTo10(total[i] - total[k]) : Math.min(opt[i][j], opt[k][j - 1] + roundTo10(total[i] - total[k]));
                }
            }
        }
        
        System.out.println(opt[n][d]);
    }
}