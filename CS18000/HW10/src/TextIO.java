// Lecture 19, Slide 20

/* Here is an example program that deals specifically with characters and not
 * just bytes. Note too that we open the file for append. Run the program
 * multiple times and note its impact on the contents of the textio.txt file.
 */
import java.io.*;
import java.util.Scanner;

public class TextIO {
  public static void main(String[] args) throws IOException {
    File f = new File("textio.txt");

    // Optional second argument specifies append
    FileOutputStream fos = new FileOutputStream(f, false);

    /* Much like ObjectOutputStream and DataOutputStream, PrintWriter converts
     * our text data into a stream of bytes, that FileOutputStream then writes
     * to the file.
     */
    PrintWriter pw = new PrintWriter(fos);
    // PrintWriter has more text-friendly methods.
    for (int i = 0; i < 5; i++) {
      pw.println("no");
    }
    // Always!
    pw.close();

    /* Here we turn around and read it back, but instead of using
     * FileInputStream, we use FileReader and BufferedReader. Both of these are
     * geared specifically towards streams of characters. Not just bytes.
     */
    FileReader fr = new FileReader(f);
    BufferedReader bfr = new BufferedReader(fr);
    while (true) {
      String s = bfr.readLine();
      // null signifies EOF
      if (s == null)
        break;
      System.out.println(s);
    }
    // Don't forget!
    bfr.close();
  }
}
