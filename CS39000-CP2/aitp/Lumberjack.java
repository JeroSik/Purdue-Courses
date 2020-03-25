import java.io.*;
import java.util.*;
import java.lang.*;

public class Lumberjack {
    public static ArrayList<ArrayList<Integer>> adj = new ArrayList<ArrayList<Integer>>();
    public static int[] count;
    public static HashSet<Integer> s = new HashSet<Integer>();
    public static HashSet<Integer> visited = new HashSet<Integer>();
    public static int n;

    public static void numberOfNodes(int c, int e) { 
        count[c] = 1;

        for(int u: adj.get(c)) { 
            if(u == e) {
                continue;
            } 
            
            numberOfNodes(u ,c); 
            count[c] += count[u]; 
        }
    }

    public static void DFS(int c) {
        for (int a : adj.get(c)) {
            if (!visited.contains(a)) {
                int child = count[a];
                s.add(child);
                s.add(n - child);
                visited.add(a);
                DFS(a);
            }
        }
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        n = scan.nextInt();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<Integer>());
        }
        count = new int[n];

        for (int i = 0; i < n - 1; i++) {
            int u = scan.nextInt() - 1;
            int v = scan.nextInt() - 1;
            adj.get(u).add(v);
            adj.get(v).add(u);
        }

        numberOfNodes(0, 0);

        visited.add(0);
        DFS(0);

        List<Integer> list = new ArrayList<Integer>(s); 
        Collections.sort(list);
        for (int x : list) {
            out.println(x); 
        }

        out.close();
    }
}