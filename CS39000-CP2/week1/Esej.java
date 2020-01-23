import java.util.*;

public class Esej {
  public static String[] alphabet = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};

  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);

    int A = scan.nextInt();
    int B = scan.nextInt();
    String[] ans = new String[B];

    for (int i = 0; i < B; i++) {
      ans[i] = generateWord(i);
    }

    for (String word : ans) {
      System.out.print(word + " ");
    }
  }

  public static String generateWord(int n) {
    String word = "";

	  do {
      word += alphabet[n % 26];
      n = n / 26;
    } while (n > 0);

	  return word;
  }
}