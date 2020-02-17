import java.util.*;

public class TrainSorting {
    public static int findMaxTrains(ArrayList<Integer> trains, int n) {
        int[] lis = new int[n];
        int[] lds = new int[n];
        Collections.reverse(trains);
        Arrays.fill(lis, 1);
        Arrays.fill(lds, 1);
        int maxTrains = 0;

        for (int i = 0; i < n; i++) {
            for (int j = i - 1; j >= 0; j--) {
                if (trains.get(i) < trains.get(j)) {
                    lis[i] = Math.max(lis[j] + 1, lis[i]);
                } else if (trains.get(i) > trains.get(j)) {
                    lds[i] = Math.max(lds[j] + 1, lds[i]);
                }
            }
            maxTrains = Math.max(maxTrains, lis[i] + lds[i] - 1);
        }

        return maxTrains;
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int n = scan.nextInt();
        ArrayList<Integer> trains = new ArrayList<Integer>();
        for (int i = 0; i < n; i++) {
            trains.add(scan.nextInt());
        }

        System.out.println(findMaxTrains(trains, n));
    }
}