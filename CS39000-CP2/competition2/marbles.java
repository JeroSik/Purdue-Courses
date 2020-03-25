import java.util.*;
import java.io.*;
import java.lang.*;

public class marbles {
  public static int N;
  public static int M;
  public static ArrayList<Integer> marbles = new ArrayList<Integer>();
  public static ArrayList<ArrayList<Integer>> adj = new ArrayList<ArrayList<Integer>>();
  public static HashSet<Integer> tree = new HashSet<Integer>();
  public static int answer = 0;


  public static void DFS(int node, int maxCount, int count) {
    if (adj.get(node).size() == 0) {
      if (maxCount <= M) {
        answer++;
      }
      return;
    }

    for (int child : adj.get(node)) {        
      int newCount = count;
      int newMax = maxCount;
      if (marbles.get(child) == 1) {
        newCount++;
      } else {
        newCount = 0;
      }

      newMax = Math.max(newCount, newMax);

      DFS(child, newMax, newCount);
    }
  }

  public static void main(String[] args) throws IOException {
    Scanner scan = new Scanner(System.in);

    N = scan.nextInt();
    M = scan.nextInt();

    for (int i = 0; i < N; i++) {
      marbles.add(scan.nextInt());
    }

    for (int i = 0; i < N; i++) {
      adj.add(new ArrayList<Integer>());
    }

    tree.add(0);
    for (int i = 0; i < N - 1; i++) {
      int a = scan.nextInt() - 1;
      int b = scan.nextInt() - 1;

      if (tree.contains(a)) {
        adj.get(a).add(b);
        tree.add(b);
      } else {
        adj.get(b).add(a);
        tree.add(a);
      }
    }

    DFS(0, marbles.get(0), marbles.get(0));

    System.out.println(answer);
  }
}