import java.util.*;

public class King {
  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);

    int n = scan.nextInt();
    int count = 0;

    for (int i =0; i<n;i++) {
        int x = scan.nextInt();
        if (x %2 == 0) {
            count++;
        }
        if (count > 1) {
            System.out.println("YES");
            return;
        }
    }
    System.out.println("NO");

  }
}