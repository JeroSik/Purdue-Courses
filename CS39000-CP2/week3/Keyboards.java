import java.util.*;

public class Keyboards {
    public static int getMinimumSwitches(ArrayList<HashSet<Integer>> instruments, int[] notes, int n, int m) {
        int[][] opt = new int[n][m + 1];
        for (int i = 0; i < n; i++) {
            opt[i][0] = 0;
        }

        int prevMin = 0;
        for (int j = 1; j <= m; j++) {
            int currMin = 1001;
            for (int i = 0; i < n; i++) {
                if (instruments.get(i).contains(notes[j - 1])) {
                    opt[i][j] = opt[i][j - 1];
                } else {
                    opt[i][j] = prevMin + 1;
                }

                if (j != m && instruments.get(i).contains(notes[j])) {
                    currMin = Math.min(currMin, opt[i][j]);
                }
            }
            prevMin = currMin;
        }

        int minSwitch = opt[0][m];
        for (int i = 1; i < n; i++) {
            minSwitch = Math.min(minSwitch, opt[i][m]);
        }

        return minSwitch;
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int n = scan.nextInt();
        int m = scan.nextInt();

        ArrayList<HashSet<Integer>> instruments = new ArrayList<HashSet<Integer>>();
        int[] notes = new int[m];
        for (int i = 0; i < n; i++) {
            int k = scan.nextInt();
            HashSet<Integer> playable = new HashSet<Integer>();
            for (int j = 0; j < k; j++) {
                playable.add(scan.nextInt());
            }
            
            instruments.add(playable);
        }

        for (int i = 0; i < m; i++) {
            notes[i] = scan.nextInt();
        }
    
        System.out.println(getMinimumSwitches(instruments, notes, n, m));
    }
}