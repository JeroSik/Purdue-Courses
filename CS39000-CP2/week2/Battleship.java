import java.util.*;
import java.lang.*;

public class Battleship {
    public static String findWinner(int[][] map1, int[][] map2, int ships1, int ships2, int[][] shots) {
        int turn = 1;
        boolean redemption = false;

        int i;
                // System.out.priXntln(ships1 + " " + ships2);

        for (i = 0 ; i < shots.length; i++) {
            int x = shots[i][0];
            int y = map1.length - shots[i][1] - 1;

            if (turn == 1) {
                if (map2[y][x] == 0) {
                    // System.out.println("M1");
                    turn = 2;
                } else {
                    // System.out.println("H1");
                    map2[y][x] = 0;
                    ships2--;
                    if (ships2 == 1) {
                        redemption = true;
                        break;
                    }
                }
            } else {
                if (map1[y][x] == 0) {
                    // System.out.println("M2");
                    turn = 1;
                } else {
                    // System.out.println("H2");
                    map1[y][x] = 0;
                    ships1--;
                    if (ships1 == 1) {
                        break;
                    }
                }
            }
        }

        if (redemption) {
            for (i = i; i < shots.length; i++) {
                int x = shots[i][0];
                int y = map1.length - shots[i][1] - 1;

                if (map1[y][x] == 0) {
                    turn = 1;
                } else {
                    map1[y][x] = 0;
                    ships1--;
                    if (ships1 == 1) {
                        break;
                    }
                }
            }
        }

        // System.out.println(ships1 + " " + ships2);
        if (ships1 == ships2) {
            return "draw";
        } else if (ships1 == 1) {
            return "player two wins";
        } else if (ships2 == 1) {
            return "player one wins";
        } else {
            return "draw";
        }
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int t = scan.nextInt();
        for (int i = 0; i < t; i++) {
            int w = scan.nextInt();
            int h = scan.nextInt();
            int n = scan.nextInt();
            scan.nextLine();

            int[][] map1 = new int[h][w];
            int[][] map2 = new int[h][w];
            int ships1 = 1;
            for (int j = 0; j < h; j++) {
                String line = scan.nextLine();
                for (int k = 0; k < w; k++) {
                    if (line.charAt(k) == '#') {
                        map1[j][k] = ships1;
                        ships1++;
                    } else {
                        map1[j][k] = 0;
                    }
                }
            }
            int ships2 = 1;
            for (int j = 0; j < h; j++) {
                String line = scan.nextLine();
                for (int k = 0; k < w; k++) {
                    if (line.charAt(k) == '#') {
                        map2[j][k] = ships2;
                        ships2++;
                    } else {
                        map2[j][k] = 0;
                    }
                }
            }

            int[][] shots = new int[n][2];
            for (int j = 0; j < n; j++) {
                shots[j][0] = scan.nextInt();
                shots[j][1] = scan.nextInt();
            }

            System.out.println(findWinner(map1, map2, ships1, ships2, shots));
        }
    }
}