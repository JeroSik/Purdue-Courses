public class GrandDeluxe extends Room {
  private int rate;

  public GrandDeluxe(int rate) {
    super(rate);
    this.rate = rate;
  }

  @Override
  public void print() {
    System.out.println("The rate for grand deluxe room is $" + rate);
  }
}
