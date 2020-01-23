import java.math.*;
import java.util.*;

public class FruitBaskets {
    public static int n, k;
    public static int[] a;
    public static ArrayList<List<Integer>> res;

    public static void rec(int i, int j) {  
        if (j == k) {
            ArrayList<Integer> list = new ArrayList<>(k);//number from 1-k
            for (int x : a)  list.add(x);
            res.add(list);
        } else {
            a[j] = i;      rec(i+1, j+1);
            if (n-i >= k-j)   rec(i+1,j);
        }
    }

    public static List<List<Integer>> getCombinations(int m, int b) {
        n = m;
        k = b;
        a = new int[b];
        res = new ArrayList<>();
        rec(1, 0);
        return res;
    }

    public static long getFruitBaskets(List<List<Integer>> comb, List<Integer> fruits) {
        long total = 0;

        for (List<Integer> basket : comb) {
            long sum = 0;

            for (int fruit : basket) {
                sum += fruits.get(fruit - 1);
            }

            if (sum < 200) {
                total += sum;
            }
        }

        return total;
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        int N = scan.nextInt();
        List<Integer> fruits = new ArrayList<>();
  
        long total = 0;
        long appearance = (1L << (N - 1));
        for (int i = 0; i < N; i++) {
            fruits.add(scan.nextInt());
            total += fruits.get(i) * appearance;
        }

        for (int i = 1; i <= 3; i++) {
            List<List<Integer>> comb = getCombinations(N, i);
            total -= getFruitBaskets(comb, fruits);
        }

        System.out.println(total);
    }
}