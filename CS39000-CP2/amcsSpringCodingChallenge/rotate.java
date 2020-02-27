import java.util.*;

public class rotate {
  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    ArrayList<Integer> a = new ArrayList<Integer>();
    while (scan.hasNext()) {
        a.add(scan.nextInt());
    }

    int k = a.get(a.size()-1);
    int n = a.get(a.size()-2);

    while (k >= n) {
        k -= n;
    }
    while (k < 0) {
        k += n;
    }

    for (int i = 0; i < n; i++) {
        System.out.print(a.get(k) + " ");
        if (++k == n) {
            k = 0;
        }
    }
  }
}