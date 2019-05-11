/**
 * This will be the blueprint for a car.
 * A car has three traits.  A year it was made, a make (Ford, GM, etc.), and a color.
 */
public class Car {
  private String year, make, color; // TODO: Declare three field variables for the three traits of a car

  public Car (String year, String make, String color){
    this.year = year;
    this.make = make;
    this.color = color;
  }// TODO: Implement the constructor of Car. It will initialize the three fields you declared. Using the constructor, you can create an instance of Car by
  // Car car = new Car("1992", "ford", "red");

  // TODO: Define other methods of Car you want to use
  public String getYear () {
    return this.year;
  }

  public String getMake () {
    return this.make;
  }

  public String getColor () {
    return this.color;
  }
}