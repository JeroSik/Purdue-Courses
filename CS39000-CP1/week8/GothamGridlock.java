import java.io.*;
import java.util.*;

public class Solution {

    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        Scanner scan = new Scanner(System.in);
        int n = Integer.parseInt(scan.nextLine());
        List<String> key = new ArrayList<String>();
        int[][] map = new int[2 * n][2 * n];
        
        for (int i = 0; i < n; i++) {
            String[] streets = scan.nextLine().split(" ");
            int a, b;
            if (key.contains(streets[0]) && key.contains(streets[1])) {
                a = key.indexOf(streets[0]);
                b = key.indexOf(streets[1]);
                map[a][b] = 1;
                map[b][a] = 1;
            } else if (key.contains(streets[0])) {
                a = key.indexOf(streets[0]);
                key.add(streets[1]);
                b = key.size() - 1;
                map[a][b] = 1;
                map[b][a] = 1;
            } else if (key.contains(streets[1])) {
                a = key.indexOf(streets[1]);
                key.add(streets[0]);
                b = key.size() - 1;
                map[a][b] = 1;
                map[b][a] = 1;
            } else if (streets[0].equals(streets[1])) {
                key.add(streets[0]);
                a = key.size() - 1;
                map[a][a] = 1;
            } else {
                key.add(streets[0]);
                a = key.size() - 1;
                key.add(streets[1]);
                b = key.size() - 1;
                map[a][b] = 1;
                map[b][a] = 1;
            }
        }
        int m = Integer.parseInt(scan.nextLine());
        for (int i = 0; i < m; i++) {
            String[] streets = scan.nextLine().split(" ");
            int start = key.indexOf(streets[0]);
            int end = key.indexOf(streets[1]);
            System.out.println(possiblePath(start, end, key, map));
        }
    }
    
    public static String possiblePath(int start, int end, List<String> key, int[][] map) {
        Queue<Integer> q = new LinkedList<Integer>();
        boolean[][] visited = new boolean[map.length][map[0].length];
        q.add(start);
        while (!q.isEmpty()) {
            int curr = q.poll();
            if (curr == end) {
                return "YES";
            }
            for (int i = 0; i < map[curr].length; i++) {
                if (map[curr][i] == 1) {
                    if (!visited[curr][i]) {
                        q.add(i);
                        visited[curr][i] = true;
                    }
                }
            }
        }
        return "NO";
    }
}
