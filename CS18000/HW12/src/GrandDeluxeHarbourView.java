public class GrandDeluxeHarbourView extends Room {
  private int rate;

  public GrandDeluxeHarbourView(int rate) {
    super(rate);
    this.rate = rate;
  }

  @Override
  public void print() {
    System.out.println("The rate for grand deluxe harbour view room is $" + rate);
  }
}
