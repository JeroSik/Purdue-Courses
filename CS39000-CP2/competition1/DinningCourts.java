import java.util.*;
import java.lang.*;

public class DinningCourts {
    public static int findDinningCourt(int[] line, int n) {
        int pos = 0;

        while (line[pos] > 0) {
            for (int i = 0; i < n; i++) {
                line[i] = line[i] - 1;
            }

            pos = (pos + 1) % n;

        }

        return pos + 1;
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int n = scan.nextInt();
        int[] line = new int[n];

        for (int i = 0; i < n; i++) {
            line[i] = scan.nextInt();
        }

        System.out.println(findDinningCourt(line, n));
    }
}