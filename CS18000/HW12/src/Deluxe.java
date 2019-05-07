public class Deluxe extends Room {
  private int rate;

  public Deluxe(int rate) {
    super(rate);
    this.rate = rate;
  }

  @Override
  public void print() {
    System.out.println("The rate for deluxe room is $" + rate);
  }
}
