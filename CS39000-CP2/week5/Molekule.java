import java.util.*;
import java.lang.*;

public class Molekule {
    public static ArrayList<ArrayList<Integer>> adj;
    public static int[][] edges;
    public static int[] color;
    public static HashSet<Integer> visited;

    public static void DFS(int curr) {
        for (int next : adj.get(curr)) {
            if (!visited.contains(next)) {
                visited.add(next);

                color[next] = color[curr] == 0 ? 1 : 0;
                DFS(next);
            }
        }
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int N = scan.nextInt();

        visited = new HashSet<Integer>();
        edges = new int[N - 1][2];
        color = new int[N];
        adj = new ArrayList<ArrayList<Integer>>(N);
        for (int i = 0; i < N; i++) {
            adj.add(new ArrayList<Integer>());
        }

        for (int i = 0; i < N - 1; i++) {
            int a = scan.nextInt() - 1;
            int b = scan.nextInt() - 1;

            edges[i][0] = a;
            edges[i][1] = b;
            adj.get(a).add(b);
            adj.get(b).add(a);
        }

        visited.add(0);
        color[0] = 0;
        DFS(0);

        for (int[] edge : edges) {
            int a = edge[0];
            int b = edge[1];
            if (color[a] == 0 && color[b] == 1) {
                System.out.println(0);
            } else if (color[a] == 1 && color[b] == 0) {
                System.out.println(1);
            } else {
                System.out.println("ERROR");
            }
        }
    }
}