import java.util.Scanner;

public class BusinessCard {
  public static void main (String[] args) {
    Scanner scan = new Scanner(System.in);

    System.out.print("Enter your name: ");
    String name = scan.nextLine();
    System.out.print("Enter your age: ");
    int age = scan.nextInt();
    System.out.print("Enter your GPA: ");
    double gpa = scan.nextDouble();
    scan.nextLine();
    System.out.print("Enter your major: ");
    String major = scan.nextLine();
    System.out.print("Enter your email: ");
    String email = scan.nextLine();
    System.out.println();

    System.out.printf("Name:  %1s\n", name);
    System.out.printf("Age: %4s\n", age);
    System.out.printf("GPA: %6.2f\n", gpa);
    System.out.printf("Major: %1s\n", major);
    System.out.printf("Email: %1s\n", email);
  }
}