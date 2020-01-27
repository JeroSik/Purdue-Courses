import java.util.*;

public class TrainSorting {
    // public static int findLIS(int[] nums, int n) {
    //     int[] opt = new int[n];
    //     opt[0] = 1;
    //     int maxLIS = 0;

    //     for (int i = 1; i < n; i++) {
    //         opt[i] = 0;

    //         for (int j = 0; j < i; j++) {
    //             if (nums[i] > nums[j]) {
    //                 opt[i] = Math.max(opt[i], opt[j]);
    //             }
    //         }
    //         opt[i] = opt[i] + 1;
    //         maxLIS = Math.max(maxLIS, opt[i]);
    //     }

    //     return maxLIS;
    // }

    // public static int findLDS(int[] nums, int n) {
    //     int[] opt = new int[n];
    //     opt[0] = 1;
    //     int maxLDS = 0;

    //     for (int i = 1; i < n; i++) {
    //         opt[i] = 0;

    //         for (int j = 0; j < i; j++) {
    //             if (nums[i] < nums[j]) {
    //                 opt[i] = Math.max(opt[i], opt[j]);
    //             }
    //         }
    //         opt[i] = opt[i] + 1;
    //         maxLDS = Math.max(maxLDS, opt[i]);
    //     }

    //     return maxLDS;
    // }

    public static int findMaxTrains(int[] nums, int n) {
        int[] lis = new int[n];
        int[] lds = new int[n];
        lis[0] = 1;
        lds[0] = 1;
        int maxTrains = 0;

        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] < nums[j]) {
                    lis[i] = Math.max(lis[i], lis[j]);
                } else if (nums[i] > nums[j]) {
                    lds[i] = Math.max(lds[i], lds[j]);
                }
            }

            lis[i] = lis[i] + 1;
            lds[i] = lds[i] + 1;

            maxTrains = Math.max(maxTrains, lis[i] + lds[i] - 1);
        }

        return maxTrains;
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int n = scan.nextInt();
        int[] cars = new int[n];
        for (int i = 0; i < n; i++) {
            cars[i] = scan.nextInt();
        }
    
        System.out.println(findMaxTrains(cars, n));
    }
}