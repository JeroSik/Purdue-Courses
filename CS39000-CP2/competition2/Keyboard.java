import java.util.*;
import java.io.*;
import java.lang.*;

public class Keyboard {
  static class  Node {
    public char data;
    public Node next;

    Node() {
      data = '\0';
      next = null;
    }

    Node(char c, Node n) {
      data = c;
      next = n;
    }
  }

  public static void main(String[] args) throws IOException {
    Scanner scan = new Scanner(System.in);
    // FastReader fr = new FastReader(System.in);
    PrintWriter out = new PrintWriter(System.out);
		
    while (true) {
      String s = scan.nextLine();
      if (s.equals("0")) {
        break; 
      }

      Node curr = new Node();
      Node start = curr;
      Node end = curr;

      for (int i = 0; i < s.length(); i++) {
        if (s.charAt(i) == '@') {
          curr = start;
        } else if (s.charAt(i) == '$') {
          curr = end;
        } else {
          Node temp = new Node(s.charAt(i), curr.next);
          curr.next = temp;

          end = curr == end ? temp : end;
          curr = temp;
        }
      }

      while (start != null) {
        if (start.data != '\0') {
          out.print(start.data);
        }
        start = start.next;
      }
      out.println();
    }
    out.close();
  }
}