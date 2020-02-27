import java.util.*;

public class flip {
  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);

    String s = scan.nextLine();
    boolean onlyOne = true;
    int n = s.length();
    int ans = 0;
    int curr = 0;
    int prev = 0;
    int counter1 = 0;
    curr = 0;

    for (int i = 0; i < n; i++) {
        if (s.charAt(i) == '0') {
            if (i + 1 != n && s.charAt(i + 1) == '0') {
                prev = 0;
            } else if (i + 1 != n && s.charAt(i + 1) == '1') {
                prev = curr;
            }
            curr = 0;
            onlyOne = false;
        } else if (s.charAt(i) == '1') {
            curr++;
        }

        ans = Math.max(curr + prev, ans);
    }

    if (onlyOne) {
        System.out.println(s.length());
        return;
    }
    System.out.println(ans + 1);
  }
}