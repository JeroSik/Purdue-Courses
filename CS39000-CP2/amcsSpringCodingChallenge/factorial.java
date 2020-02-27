import java.util.*;

public class factorial {
  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    int n = scan.nextInt();

    if (n < 0) {
        System.out.println("Error");
        return;
    } else if (n == 0 || n == 1) {
        System.out.println(1);
        return;
    }

    double curr = n;
    for (int i = 2; i < n; i++) {
        double prev = 0;
        for (int j = 0; j < i; j++) {
            prev += curr;
        }
        curr = prev;
    }

    
    System.out.printf("%.0f", curr);
  }
}