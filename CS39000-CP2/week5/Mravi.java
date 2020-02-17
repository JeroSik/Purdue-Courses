import java.util.*;
import java.io.*;
import java.lang.*;

public class Mravi {
  public static double amount = 0;

  public static double DFS(int v, double x, int t, int p, ArrayList<ArrayList<int[]>> adj, int[] needs) {
    double total = 0;

    if (needs[v] != -1) {
      total = t == 1 ? Math.sqrt((double) needs[v]) : needs[v];
      return total / x;
    }

    double subtree = 0;
    for (int[] edge : adj.get(v)) {  
      if (edge[0] == p) {
          continue;
      }

      total = Math.max(total, DFS(edge[0], ((double) edge[1]) / 100, edge[2], v, adj, needs));
    }

    if (t == 1) {
      total = Math.sqrt(total);
    }
    return total / x;
  }

  public static void main(String[] args) throws IOException {
    Scanner scan = new Scanner(System.in);

    int N = scan.nextInt();

    int[] needs = new int[N];
    ArrayList<ArrayList<int[]>> adj = new ArrayList<ArrayList<int[]>>(N);
    for (int i = 0; i < N; i++) {
      adj.add(new ArrayList<int[]>());
    }

    for (int i = 0; i < N - 1; i++) {
      int A = scan.nextInt() - 1;
      int B = scan.nextInt() - 1;
      int X = scan.nextInt();
      int T = scan.nextInt();

      adj.get(A).add(new int[]{B, X, T});
      adj.get(B).add(new int[]{A, X, T});
    }
    for (int i = 0; i < N; i++) {
      needs[i] = scan.nextInt();
    }

    System.out.println(DFS(0, 1.0, 0, 0, adj, needs));
  }
}