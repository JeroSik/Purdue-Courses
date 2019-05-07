import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class ChatFilter {

  private File badWordsFile;

  public ChatFilter(String badWordsFileName) throws IllegalArgumentException {
    if(!badWordsFileName.endsWith(".txt")) {
      throw new IllegalArgumentException("Invalid file name!");
    }

    badWordsFile = new File(badWordsFileName);
  }

  public String filter(String msg) throws IOException {

    String out = msg;
    Scanner sc = new Scanner(badWordsFile);

    while (sc.hasNextLine()) {
      String in = sc.nextLine();
      String rep = "";
      for (int i = 0; i < in.length(); i++) {
        rep += "*";
      }

      out = out.replaceAll("(?i)" + in, rep);
    }

    return out;
  }
}