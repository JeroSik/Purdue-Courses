import java.util.*;

public class internal {
  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    int c = scan.nextInt();
    int n = scan.nextInt();

    double d = 0;

    for (int i = 0; i < n; i++) {
        double f = scan.nextDouble();

        d += Math.ceil(f / ((double) c));
    }

    double ans = d * (double)c;

    System.out.printf("%.0f", ans);
  }
}