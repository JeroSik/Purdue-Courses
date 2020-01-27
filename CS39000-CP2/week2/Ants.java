import java.util.*;
import java.lang.*;

public class Ants {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int t = scan.nextInt();
        for (int i = 0; i < t; i++) {
            int l = scan.nextInt();
            int n = scan.nextInt();

            int ant;
            int shortest = 0;
            int longest = 0;
            for (int j = 0; j < n; j++) {
                ant = scan.nextInt();
                shortest = Math.max(shortest, Math.min(ant, l - ant));
                longest = Math.max(longest, Math.max(ant, l - ant));
            }
            
            System.out.println(shortest + " " + longest);
        }
    }
}