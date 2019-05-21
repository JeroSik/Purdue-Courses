import java.util.Random;

public class test {
  public static void main(String[] args) {
    int seed = Integer.parseInt(args[0]);
    Random r = new Random(seed);
    String original = "college";
    String temp;
    String updated = "";
    while (!(original.equals(""))) {
      int letter = r.nextInt(original.length());
      updated += original.charAt(letter);
      temp = original.substring(0, letter) + original.substring(letter + 1, original.length());
      for (int j = 0;j < temp.length(); j++) {
        if (original.charAt(letter) == temp.charAt(j)) {
          updated += temp.charAt(j);
          temp = temp.substring(0, j) + temp.substring(j + 1, temp.length());
        }
      }
      original = temp;
    }
    System.out.println(updated);
  }
}
