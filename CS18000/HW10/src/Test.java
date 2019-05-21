public class Test {
  public static void main(String[] args) {
    String read = "test test test asfdtestasdf no";
    String original = "test";
    String replace = "good";
    int count = 0;
    for (int i = 0; i < read.length(); i++) {
      if (i + original.length() > read.length()) {
        break;
      }
      if (original.equals(read.substring(i, i + original.length()))) {
        read = read.substring(0, i) + replace + read.substring(i + original.length(), read.length());
        count++;
      }
    }
    System.out.println(read);
  }
}
