import java.util.Scanner;

public class ParkingLotClient {
  public static void main(String[] args) {
    ParkingLot lot = new ParkingLot(6);
    Scanner scan = new Scanner(System.in);

    while (true) {
      System.out.println("==========================================================");
      System.out.println("Welcome to the CS180 car lot, what would you like to do?");
      System.out.println("1 - List all cars");
      System.out.println("2 - Remove a car from the lot");
      System.out.println("3 - Add a car to the lot");
      System.out.println("4 - Search car lot by year");
      System.out.println("5 - Search car lot by color");
      System.out.println("6 - Search car lot by make");
      System.out.println("7 - Quit");
      System.out.println("==========================================================");
      System.out.println("Choice: ");
      int choice = scan.nextInt();
      switch (choice) {
        case 1:
          for (int i = 0; i < lot.amountCar(); i++) {
            lot.printCarDetails(i);
          }
          break;
        case 2:
          System.out.println("Input the spot number of the car you want to remove:");
          int spotNumber = scan.nextInt();
          if (lot.removeCar(spotNumber)) {
            System.out.println("Removal succeed.");
          } else {
            System.out.println("There is no car there!");
          }
          break;
        case 3:
          System.out.println("Year: ");
          String year = scan.next();
          System.out.println("Color: ");
          String color = scan.next();
          System.out.println("Make: ");
          String make = scan.next();
          Car insert = new Car(year, make, color);
          if (lot.addCar(insert)) {
            System.out.println("Adding succeed.");
          } else {
            System.out.println("Sorry, CS180 car lot is full. Cannot park any more cars.");
          }
          break;
        case 4:
          System.out.println("Year: ");
          String yearSearch = scan.next();
          for (int i = 0; i < lot.amountCar(); i++) {
            if (lot.searchByYear(yearSearch)[i]) {
              lot.printCarDetails(i);
            }
          }
          int count = 0;
          for (int i = 0; i < lot.amountCar(); i++) {
            if (!lot.searchByYear(yearSearch)[i]) {
              count++;
            }
          }
          if (count == 6) {
            System.out.println("No cars found");
          }
          break;
        case 5:
          System.out.println("Color: ");
          String colorSearch = scan.next();
          for (int i = 0; i < lot.amountCar(); i++) {
            if (lot.searchByColor(colorSearch)[i]) {
              lot.printCarDetails(i);
            }
          }
          count = 0;
          for (int i = 0; i < lot.amountCar(); i++) {
            if (!lot.searchByColor(colorSearch)[i]) {
              count++;
            }
          }
          if (count == 6) {
            System.out.println("No cars found");
          }
          break;
        case 6:
          System.out.println("Make: ");
          String makeSearch = scan.next();
          for (int i = 0; i < lot.amountCar(); i++) {
            if (lot.searchByMake(makeSearch)[i]) {
              lot.printCarDetails(i);
            }
          }
          count = 0;
          for (int i = 0; i < lot.amountCar(); i++) {
            if (!lot.searchByMake(makeSearch)[i]) {
              count++;
            }
          }
          if (count == 6) {
            System.out.println("No cars found");
          }
          break;
        case 7:
          return;
        default:
          System.out.println("Unrecognized Command, please try again");
          break;
      }
    }
  }
}
