import java.util.*;
import java.lang.*;

public class CarnivalHopscotch {
    public static int findLowestScore(int[] points, int D, int N) {
        int[] opt = new int[N + 1];
        opt[0] = 0;

        for (int i = 1; i <= N; i++) {
            int lowest = Integer.MAX_VALUE;
            for (int j = i - 1; j >= i - D; j--) {
                if (j < 0) {
                    break;
                }
                lowest = Math.min(lowest, opt[j]);
            }
            opt[i] = lowest + points[i];
        }

        return opt[N];
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int N = scan.nextInt();
        int D = scan.nextInt();

        int[] points = new int[N + 1];

        for (int i = 0; i < N; i++) {
            points[i] = scan.nextInt();
        }
        points[N] = 0;

        System.out.println(findLowestScore(points, D, N));
    }
}