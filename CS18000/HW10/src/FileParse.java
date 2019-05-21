import java.io.*;

public class FileParse {
  public static int countWords(String fileName, String searchWord) throws Exception {
    FileReader fr = new FileReader(fileName);
    BufferedReader bfr = new BufferedReader(fr);
    String read = bfr.readLine();

    int count = 0;
    while(read != null) {
      for (int i = 0; i < read.length(); i++) {
        if (i + searchWord.length() > read.length()) {
          break;
        }
        if (searchWord.equals(read.substring(i, i + searchWord.length()))) {
          count++;
        }
      }
      read = bfr.readLine();
    }

    System.out.println("Total number of occurrences: " + count);
    bfr.close();
    return count;
  }

  public static boolean deleteLine(String fileName, int lineNum) throws Exception {
    FileReader fr = new FileReader(fileName);
    BufferedReader bfr = new BufferedReader(fr);

    String check = bfr.readLine();
    int num = 0;
    while (check != null) {
      num++;
      check = bfr.readLine();
    }
    bfr.close();

    fr = new FileReader(fileName);
    BufferedReader bfr2 = new BufferedReader(fr);
    if (num < 0) {
      num = 1;
    }
    String[] lines = new String[num];
    int currentLine = 0;
    boolean deleted = false;
    String read = bfr2.readLine();
    while(read != null) {
      if (currentLine != lineNum - 1) {
        lines[currentLine] = read;
      } else {
        deleted = true;
      }
      currentLine++;
      read = bfr2.readLine();
    }
    bfr2.close();

    File file = new File(fileName);
    FileOutputStream outputStream = new FileOutputStream(file, false);
    PrintWriter writer = new PrintWriter(outputStream);
    for (int i = 0; i < lines.length; i++) {
      if (i != lineNum - 1)
       writer.println(lines[i]);
    }
    writer.close();

    if (deleted) {
      System.out.println("Line deleted!");
      return true;
    } else {
      System.out.println("Line not found");
      return false;
    }
  }

  public static int refactor(String fileName, String original, String replace) throws Exception {
    FileReader fr = new FileReader(fileName);
    BufferedReader bfr = new BufferedReader(fr);
    String check = bfr.readLine();
    int num = 0;
    while (check != null) {
      num++;
      check = bfr.readLine();
    }
    bfr.close();

    fr = new FileReader(fileName);
    BufferedReader bfr2 = new BufferedReader(fr);
    if (num < 0) {
      num = 1;
    }
    String[] lines = new String[num];
    int currentLine = 0;
    String read = bfr2.readLine();
    int count = 0;
    boolean same;
    while (read != null) {
      same = true;
      for (int i = 0; i < read.length(); i++) {
        if (i + original.length() > read.length()) {
          break;
        }
        if (original.equals(read.substring(i, i + original.length()))) {
          read = read.substring(0, i) + replace + read.substring(i + original.length(), read.length());
          lines[currentLine] = read;
          same = false;
          count++;
        }
      }
      if (same) {
        lines[currentLine] = read;
      }
      currentLine++;
      read = bfr2.readLine();
    }
    bfr2.close();

    File file = new File(fileName);
    FileOutputStream outputStream = new FileOutputStream(file, false);
    PrintWriter writer = new PrintWriter(outputStream);
    //if (count != 0) {
      for (int i = 0; i < lines.length; i++) {
        writer.println(lines[i]);
        System.out.println(lines[i]);
      }
    //}
    writer.close();

    if (count == 0) {
      System.out.println("No words found");
    } else {
      System.out.println("Number of words replaced: " + count);
    }
    return count;
  }

  public static void main(String[] args) throws Exception {
    FileParse test = new FileParse();
    //int count = test.countWords("count.txt", "is");
    //System.out.println(count);
    //boolean delete = test.deleteLine("delete.txt", 2);
    //System.out.println(delete);
    int refactor = test.refactor("refactor.txt", "hehexd", "lol");
    System.out.println(refactor);
  }
}