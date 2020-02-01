import java.util.*;

public class TrainSorting {
    public static int findMaxTrains(int[] trains, int n) {
        int[] lis = new int[n];
        int[] lds = new int[n];
        int maxTrains = 0;

        for (int i = n - 1; i >= 0; i--) {
            lis[i] = 1;
            for (int j = i + 1; j < n; j++) {
                if (trains[i] < trains[j]) {
                    lis[i] = Math.max(lis[j] + 1, lis[i]);
                }
            }
        }

        for (int i = n - 1; i >= 0; i--) {
            lds[i] = 1;
            for (int j = i + 1; j < n; j++) {
                if (trains[i] > trains[j]) {
                    lds[i] = Math.max(lds[j] + 1, lds[i]);
                }
            }
        }

        for(int i = 0; i < n; i++) {
            maxTrains = Math.max(maxTrains, lis[i] + lds[i] - 1);
        }
        return maxTrains;
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int n = scan.nextInt();
        int[] trains = new int[n];
        for (int i = 0; i < n; i++) {
            trains[i] = scan.nextInt();
        }

        System.out.println(findMaxTrains(trains, n));
    }
}