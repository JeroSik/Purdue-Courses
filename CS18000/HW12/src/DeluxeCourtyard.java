public class DeluxeCourtyard extends Room {
  private int rate;

  public DeluxeCourtyard (int rate) {
    super(rate);
    this.rate = rate;
  }

  @Override
  public void print() {
    System.out.println("The rate for deluxe courtyard room is $" + rate);
  }
}
