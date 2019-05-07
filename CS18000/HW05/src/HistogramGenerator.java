import java.util.Scanner;

public class HistogramGenerator {
  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);

    //asks user for line and changes line to one word all upper
    System.out.print("Enter a line: ");
    String line = scan.nextLine();
    line = line.toUpperCase().replaceAll(" ", "");

    //creates 2 char arrays for the input line and alphabet
    char[] phrase = new char[line.length()];
    for (int i = 0; i < line.length(); i++) {
      phrase[i] = line.charAt(i);
    }
    char[] alphabet = new char[26];
    int num = 0;
    for (char i = 'A'; i <= 'Z'; i++) {
      alphabet[num++] = i;
    }

    //creates an int array that counts occurence of each letter
    int[] count = new int[26];
    for (int i = 0; i < alphabet.length; i++) {
      for (int j = 0; j < phrase.length; j++) {
        if (alphabet[i] == phrase[j]) {
          count[i] += 1;
        }
      }
    }

    //finds max value
    int max = -1;
    int temp;
    for (int i = 0; i < count.length; i++) {
      temp = count[i];
      if (temp > max){
        max = temp;
      }
    }

    System.out.println("\nLetter Histogram:");
    System.out.println("--------------------------");
    for(int i = max; i > 0; i--) {
      for (int j = 0; j < 26; j++) {
        if (count[j] >= i) {
          System.out.print("X");
        } else {
          System.out.print(" ");
        }
      }
      System.out.print("\n");
    }
    System.out.println("--------------------------");
    System.out.println("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
  }
}
