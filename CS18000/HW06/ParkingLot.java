import java.nio.channels.FileLockInterruptionException;

/**
 * This will be the blueprint for the actual parking lot object.
 * A parking lot has an array of cars, a maximum capacity and methods maintaining the array
 */
public class ParkingLot {
  private Car[] parkingSpot;

  public ParkingLot (int cars) {
    this.parkingSpot = new Car[cars];
  }

  public int amountCar () {
    return parkingSpot.length;
  }

  public void printCarDetails(int spotNumber) {
    if (parkingSpot[spotNumber] != null) {
      System.out.printf("%6d\t%6s\t%6s\t%6s\n", spotNumber, parkingSpot[spotNumber].getColor(), parkingSpot[spotNumber].getMake(), parkingSpot[spotNumber].getYear());
    }
  } // TODO: Print car details using the formating string given in the handout

  public boolean removeCar(int spotNumber) {
    if (parkingSpot[spotNumber] != null) {
      parkingSpot[spotNumber] = null;
      return true;
    } else {
      return false;
    }
  }

  public boolean addCar(Car c) {
    for (int i = 0; i < parkingSpot.length; i++) {
      if (parkingSpot[i] == null) {
        parkingSpot[i] = c;
        return true;
      }
    }
    return false;
  }

  public boolean[] searchByYear(String year) {
    boolean[] ifYear = new boolean[parkingSpot.length];
    for (int i = 0; i < ifYear.length; i++) {
      if (parkingSpot[i] != null) {
        if ((parkingSpot[i].getYear()).equals(year)) {
          ifYear[i] = true;
        } else {
          ifYear[i] = false;
        }
      } else {
        ifYear[i] = false;
      }
    }
    return ifYear;
  }

  public boolean[] searchByMake(String make) {
    boolean[] ifMake = new boolean[parkingSpot.length];
    for (int i = 0; i < ifMake.length; i++) {
      if (parkingSpot[i] != null) {
        if ((parkingSpot[i].getMake()).equals(make)) {
          ifMake[i] = true;
        } else {
          ifMake[i] = false;
        }
      } else {
        ifMake[i] = false;
      }
    }
    return ifMake;
  }

  public boolean[] searchByColor (String color) {
    boolean[] ifColor = new boolean[parkingSpot.length];
    for (int i = 0; i < ifColor.length; i++) {
      if (parkingSpot[i] != null) {
        if ((parkingSpot[i].getColor()).equals(color)) {
          ifColor[i] = true;
        } else {
          ifColor[i] = false;
        }
      } else {
        ifColor[i] = false;
      }
    }
    return ifColor;
  }
}