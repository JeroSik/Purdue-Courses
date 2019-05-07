import java.util.Scanner;
import java.lang.String;

public class ShapeDraw {
  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    char another;
    do {
      System.out.println("What symbol do you want to use to draw?");
      char symbol = scan.next().charAt(0);
      System.out.println("What shape do you want to draw?\n" + "1. Square\n" + "2. Right triangle\n" + "3. Inverse right triangle\n" + "4. Zig-zag");
      int type = scan.nextInt();
      System.out.println("How long do you want the shape to be?");
      int sideLength = scan.nextInt();
      switch (type) {
        case 1:
          for (int i = 0; i < sideLength; i++) {
            for (int j = 0; j < sideLength; j++) {
              System.out.print(symbol);
            }
            System.out.print("\n");
          }
          break;
        case 2:
          for (int i = 0; i < sideLength; i++) {
            for (int j = 0; j <= i; j++) {
              System.out.print(symbol);
            }
            System.out.print("\n");
          }
          break;
        case 3:
          for (int i = 0; i < sideLength; i++) {
            for (int j = sideLength; j > i; j--) {
              System.out.print(symbol);
            }
            System.out.print("\n");
          }
          break;
        case 4:
          for (int i = 0; i < (sideLength - 1); i++) {
            if ((i % 2) == 0) {//odd
              for (int j = 0; j < sideLength; j++) {
                for (int k = (sideLength - 1); k > j; k--) {
                  System.out.print(" ");
                }
                System.out.print(symbol);
                System.out.print("\n");
              }
            }
            else { //even
              for (int j = 0; j < (sideLength - 2); j++) {
                for (int k = 0; k <= j; k++){
                  System.out.print(" ");
                }
                System.out.print(symbol);
                System.out.print("\n");
              }
            }
          }
          break;
        default:
          break;
      }
      System.out.println("Do you want to draw another shape? (y/n)");
      another = scan.next().charAt(0);
    } while (another == 'y');
  }
}