public class Test {
  public static void main(String[] args) {
    String[] badWords = {"apple", "orange", "juice"};
    String msg = "I like Apple and Orange Juice.";
    for (int i = 0; i < badWords.length; i++) {
      String replace = "";
      for (int j = 0; j < badWords[i].length(); j++) {
        replace += "*";
      }
      msg = msg.replaceAll("(?i)" + badWords[i], replace);
    }
    System.out.println(msg);
  }
}
