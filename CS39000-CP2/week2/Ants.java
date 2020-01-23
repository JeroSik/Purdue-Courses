import java.util.*;
import java.lang.*;

public class Ants {
    public static int getShortest(int[] pos, int l) {
        int shortest = 0;
        for (int ant : pos) {
            shortest = Math.max(shortest, Math.min(ant, l - ant));
        }

        return shortest;
    }

    public static int getLongest(int[] pos, int l) {
        int longest = 0;
        for (int ant : pos) {
            longest = Math.max(longest, Math.max(ant, l - ant));
        }

        return longest;
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int t = scan.nextInt();
        for (int i = 0; i < t; i++) {
            int l = scan.nextInt();
            int n = scan.nextInt();

            int[] pos = new int[n];
            for (int j = 0; j < n; j++) {
                pos[j] = scan.nextInt();
            }

            System.out.println(getShortest(pos, l) + " " + getLongest(pos, l));
        }
    }
}