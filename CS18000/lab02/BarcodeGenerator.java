/**
 *CS180 - Lab02
 *
 *Creating a barcode that will have information about which building and room number a product will go.
 *
 *@author Youngsik Yoon, yoon161@purdue.edu, 4
 *
 *@version 1.0 Aug 31, 2017
 *
 */

import java.util.*;

public class BarcodeGenerator {
  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);

    System.out.print("Enter building name: ");
    String buildingName = scan.nextLine();
    System.out.print("Enter room number: ");
    int roomNumber = scan.nextInt();

    char c1 = buildingName.charAt(0);
    char c2 = buildingName.charAt(1);
    char c3 = buildingName.charAt(2);
    char c4 = buildingName.charAt(3);
    
    int b1 = (byte)c1;
    int b2 = (byte)c2 << 8;
    int b3 = (byte)c3 << 16;
    int b4 = (byte)c4 << 24;
    
    int buildingNumber = b4 + b3 + b2 + b1;
    
    System.out.println("Building name: " + buildingName);
    System.out.println("Room number: " + roomNumber);
    System.out.println("Barcode: " + buildingNumber + " " + roomNumber);
  }
}

