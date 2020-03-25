import java.io.*;
import java.util.*;
import java.lang.*;

public class Corona {
    public static ArrayList<ArrayList<Integer>> adj = new ArrayList<ArrayList<Integer>>();
    public static int[] safety;
    public static int maxS = -1;
    public static int n, m, k;

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
        PrintWriter out = new PrintWriter(System.out);

        n = scan.nextInt();
        m = scan.nextInt();
        k = scan.nextInt();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<Integer>());
        }
        safety = new int[n];
        Arrays.fill(safety, 1000000);

        for (int i = 0; i < m; i++) {
            int a = scan.nextInt()-1;
            int b = scan.nextInt()-1;
            adj.get(a).add(b);
        }

        for (int i = 0; i < k; i++) {
            safety[scan.nextInt() - 1] = 0;
        }

        // for (int i = 0; i < n; i++) {
        //     if (safety[i] == 0) {
        //         safetyDFS(i, 1);
        //     }
        // }
        // System.out.println(Arrays.toString(safety));

        // travelDFS(0, safety[0]);
        if (maxS == 1000000) {
            out.println("infinity");
        } else {
            out.println(maxS);
        }
        out.close();
    }
}