import java.util.Scanner;

public class PascalArray {
  public static void main (String[] args) {
    Scanner scan = new Scanner(System.in);
    boolean generator = true;
    boolean input = false;

    System.out.println("Welcome to the Pascal's Triangle Generator!");
    do {
      //Asks user for size
      int rows = 0;
      while (rows < 1) {
	System.out.println("How many rows do you want?");
	rows = scan.nextInt();
	if (rows < 1) {
          System.out.println("Please enter an integer greater than or equal to 1. How many rows do you want?");
      	}
      }

      //Creating the triangle
      int[][] triangle = new int[rows][];
      for (int i = 0; i < triangle.length; i++) {
        triangle[i] = new int[i + 1];
        for (int j = 0; j <= i; j++) {
          if (j == 0)      // First Column
            triangle[i][j] = 1;
          else if (j == i)      // Last value in row
            triangle[i][j] = 1;
          else
            triangle[i][j] = triangle[i - 1][j - 1] + triangle[i - 1][j];
        }
      }

      //Printing the triangle
      for (int i = 0; i < triangle.length; i++) {
        for (int j = 0; j <= i; j++) {
          System.out.print(triangle[i][j] + " ");
        }
        System.out.print("\n");
      }

      //Asks if user wants another triangle
      System.out.println("Do you want to generate another Pascal's Triangle? (y/n)");
      do {
        char another = scan.next().charAt(0);
        if (another == 'y') {
          generator = true;
          input = false;
        } else if (another == 'n') {
          generator = false;
          input = false;
        } else {
          System.out.println("ERROR: input only 'y' or 'n'. Do you want to generate another Pascal's Triangle? (y/n)");
          input = true;
        }
      } while (input);
    } while (generator);
    System.out.println("Thank you for using the Pascal's Triangle Generator");
  }
}
