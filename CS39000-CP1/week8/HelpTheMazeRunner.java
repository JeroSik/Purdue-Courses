import java.io.*;
import java.util.*;

public class Solution {
    public static class Tile {
        private int x, y, steps;
        
        private Tile(int x, int y, int steps) {
            this.x = x;
            this.y = y;
            this.steps = steps;
        }
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int M = scan.nextInt();
        int N = scan.nextInt();
        Tile start = new Tile(scan.nextInt(), scan.nextInt(), 0);
        Tile end = new Tile(scan.nextInt(), scan.nextInt(), 0);
        ArrayList<ArrayList<Tile>> maze = new ArrayList<ArrayList<Tile>>();
        for (int i = 0; i < M; i++) {
            maze.add(new ArrayList<Tile>());
            for (int j = 0; j < N; j++) {
                maze.get(i).add(new Tile(i, j, scan.nextInt()));
            }
        }
        
        int min = minSteps(M, N, start, end, maze);
        if (min == -1) {
            System.out.println("No route.");
        } else {
            System.out.println(min);
        }
    }
    
    public static int minSteps(int M, int N, Tile start, Tile end, ArrayList<ArrayList<Tile>> maze) {
        boolean[][] visited = new boolean[M][N];
        visited[start.x][start.y] = true;
        Queue<Tile> q = new LinkedList<Tile>();
        q.add(start);
        while (!q.isEmpty()) {
            Tile curr = q.poll();
            // Check if the end
            if (curr.x == end.x && curr.y == end.y) {
                return curr.steps;
            }
            // Up
            if (curr.x - 1 >= 0) {
                if (maze.get(curr.x - 1).get(curr.y).steps > 0) {
                    if (!visited[curr.x - 1][curr.y]) {
                        q.add(new Tile(curr.x - 1, curr.y, curr.steps + 1));
                        visited[curr.x - 1][curr.y] = true;
                    }
                }
            }
            // Right
            if (curr.y + 1 < N) {
                if (maze.get(curr.x).get(curr.y + 1).steps > 0) {
                    if (!visited[curr.x][curr.y + 1]) {
                        q.add(new Tile(curr.x, curr.y + 1, curr.steps + 1));
                        visited[curr.x][curr.y + 1] = true;
                    }
                }
            }
            // Down
            if (curr.x + 1 < M) {
                if (maze.get(curr.x + 1).get(curr.y).steps > 0) {
                    if (!visited[curr.x + 1][curr.y]) {
                        q.add(new Tile(curr.x + 1, curr.y, curr.steps + 1));
                        visited[curr.x + 1][curr.y] = true;
                    }
                }
            }
            // Left
            if (curr.y - 1 >= 0) {
                if (maze.get(curr.x).get(curr.y - 1).steps > 0) {
                    if (!visited[curr.x][curr.y - 1]) {
                        q.add(new Tile(curr.x, curr.y - 1, curr.steps + 1));
                        visited[curr.x][curr.y - 1] = true;
                    }
                }
            }
        }
        return -1;
    }
}
