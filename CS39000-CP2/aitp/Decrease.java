import java.util.*;
import java.lang.*;

public class Decrease {
    public static ArrayList<ArrayList<Pair<Integer, Integer>>> adj = new ArrayList<ArrayList<Pair<Integer, Integer>>>();
    public static int n, m, w;

    public static void safetyDFS(int c, int val) {
        for (int a : adj.get(c)) {
            safety[a] = Math.min(safety[a], val);
            safetyDFS(a, val + 1);
        }
    }

    public static void travelDFS(int c, int curr) {
        if (c == n - 1) {
            maxS = Math.max(maxS, curr);
        }

        for (int a : adj.get(c)) {
            int newCurr = curr;
            newCurr = Math.min(newCurr, safety[a]);
            travelDFS(a, newCurr);
        }
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        n = scan.nextInt();
        m = scan.nextInt();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<Integer>());
        }

        for (int i = 0; i < m; i++) {
            int u = scan.nextInt();
            int v = scan.nextInt();
            int w = scan.nextInt();

            adj.get(u).add(new Pair<>(v, w));
            adj.get(v).add(new Pair<>(u, w));
        }
        System.out.println(5);
    }
}