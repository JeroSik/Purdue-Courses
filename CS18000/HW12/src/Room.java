public abstract class Room {
  protected int rate;
  private String name;
  private boolean status;

  public Room(int rate) {
    this.rate = rate;
    status = true;
  }

  public boolean getStatus() {
    return status;
  }
  public void setStatus() {
    status = false;
  }

  public void setName(String name) {
    this.name = name;
  }

  public abstract void print();
}