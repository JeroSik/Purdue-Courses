import java.util.Scanner;
import java.lang.String;

public class UsernameGen {
  public static void main (String[] args) {
    Scanner scan = new Scanner(System.in);

    System.out.println("What is your full name?");
    String upperName = scan.nextLine();
    String name = upperName.toLowerCase();
    System.out.println("What is your birth year?");
    int year = scan.nextInt();
    String abbreviation = String.valueOf(2017 - year);

    char initial = name.charAt(0);
    int length = name.length();
    int firstLetter = name.indexOf(" ") + 1;
    String lastName = name.substring(firstLetter,length);
    String username = initial + lastName + abbreviation;

    System.out.println("Your username is " + username);
  }
}
