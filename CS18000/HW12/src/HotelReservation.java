/**
 * HotelReservation - a hotel reservation system
 *
 * @author
 */
import java.util.Scanner;

public class HotelReservation {
  private static final int RATE_DELUXE = 510;
  private static final int RATE_DELUXE_COURTYARD = 535;
  private static final int RATE_GRAND_DELUXE = 575;
  private static final int RATE_GRAND_DELUXE_HARBOUR_VIEW = 845;

  Room[] deluxe, deluxeCourtyard, grandDeluxe, grandDeluxeHarbourView;

  /**
   * Constructs a hotel reservation system
   */
  public HotelReservation() {
    // Todo
    deluxe = new Room[10];
    deluxeCourtyard = new Room[10];
    grandDeluxe = new Room[10];
    grandDeluxeHarbourView = new Room[10];
    for (int i = 0; i < 10; i++) {
      deluxe[i] = new Deluxe(RATE_DELUXE);
      deluxeCourtyard[i] = new DeluxeCourtyard(RATE_DELUXE_COURTYARD);
      grandDeluxe[i] = new GrandDeluxe(RATE_GRAND_DELUXE);
      grandDeluxeHarbourView[i] = new GrandDeluxeHarbourView(RATE_GRAND_DELUXE_HARBOUR_VIEW);
    }
  }

  /**
   * Makes a reservation
   *
   * @param name the name of the customer
   * @param roomType the room type chosen by the customer
   * @return a boolean value indicating if the reservation is made successfully or not
   */
  public boolean makeReservation(String name, int roomType) {
    switch (roomType) {
      case 1:
        for (int i = 0; i < 10; i++) {
          if (deluxe[i].getStatus()) {
            deluxe[i].setStatus();
            deluxe[i].setName(name);
            deluxe[i].print();
            break;
          }
        }
        return false;
      case 2:
        for (int i = 0; i < 10; i++) {
          if (deluxeCourtyard[i].getStatus()) {
            deluxeCourtyard[i].setStatus();
            deluxeCourtyard[i].setName(name);
            deluxeCourtyard[i].print();
            return true;
          }
        }
        return false;
      case 3:
        for (int i = 0; i < 10; i++) {
          if (grandDeluxe[i].getStatus()) {
            grandDeluxe[i].setStatus();
            grandDeluxe[i].setName(name);
            grandDeluxe[i].print();
            return true;
          }
        }
        return false;
      case 4:
        for (int i = 0; i < 10; i++) {
          if (grandDeluxeHarbourView[i].getStatus()) {
            grandDeluxeHarbourView[i].setStatus();
            grandDeluxeHarbourView[i].setName(name);
            grandDeluxeHarbourView[i].print();
            return true;
          }
        }
        return false;
      default:
        return false;
    }
  }

  /**
   * Example main method
   */

  public static void main(String[] args) {
    Scanner s = new Scanner(System.in);
    HotelReservation hr = new HotelReservation();

    do {
      System.out.println("Name: ");
      String name = s.next();
      System.out.println("Type of room:\n1-Deluxe\n2-DeluxeCourtyard\n3-GrandDeluxe\n4-GrandDeluxeHarbourView");
      int roomType = s.nextInt();
      hr.makeReservation(name, roomType);
    } while (true);
  }

}