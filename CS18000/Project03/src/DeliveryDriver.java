public class DeliveryDriver {
  private String name;
  private double wage;
  private PurchasedItem[] items;
  private int numItems = 0;
  private int minutesDelivering = 0;
  private int numDeliveries = 0;
  private int maxCarriableItems = 0;

  public DeliveryDriver(String name, double wage, int maxCarriableItems) {
    this.name = name;
    this.wage = wage;
    this.maxCarriableItems = maxCarriableItems;
    items = new PurchasedItem[maxCarriableItems];
  }
  public DeliveryDriver(String name, double wage) {
    this(name, wage, 5);
    items = new PurchasedItem[maxCarriableItems];
  }
  public DeliveryDriver(String name) {
    this(name, 7.25, 5);
    items = new PurchasedItem[maxCarriableItems];
  }

  public String getName() {
    return this.name;
  }
  public double getWage() {
    return this.wage;
  }
  public int getTimeSpent() {
    return this.minutesDelivering;
  }

  /**
   * * Consults the number of orders that the driver has delivered
   * @return number of orders delivered
   */
  public int getNumDelivered() {
    return this.numDeliveries;
  }
  public int getMaxCapacity() {
    return maxCarriableItems;
  }

  /**
   * Add the order to the list/array of items to deliver.
   *
   * @param item - order to add
   * @return true if the item is for delivery and if the driver can
   * hold more orders, return false otherwise
   */
  public boolean pickupOrder(PurchasedItem item) {
    if (item.isDelivery()) {
      for (int i = 0; i < items.length; i++) {
        if (numItems < maxCarriableItems) {
          items[numItems] = item;
          numItems++;
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Returns the number of items in the delivery list
   *
   * @return num items
   */
  public int getNumOrders() {
    return numItems;
  }

  /** Return an array of items to deliver.
   * the array has to be populated within the index 0 to numItems - 1
   * and of size numItems
   *
   * @return array of type PurchasedItem
   * */
  public PurchasedItem[] getOrders() {
    int count = 0;
    PurchasedItem[] onlyItems = new PurchasedItem[numItems];
    for (int i = 0; i < items.length; i++) {
      if (items[i] != null) {
        onlyItems[count] = items[i];
        count++;
      }
    }
    return onlyItems;
  }

  /**
   * Update how long the driver has been delivering and empty the
   * list of items to deliver.
   */
  public void deliverOrders() {
    for (int i = 0; i < items.length; i++) {
      if (items[i] != null) {
        minutesDelivering += items[i].getDeliveryTime();
        numDeliveries++;
        numItems = 0;
        items[i] = null;
      }
    }
  }

  /**
   * Check if driver is scheduled to deliver an order and remove it
   * and update the driver's counters if the item is found.
   *
   * @param item - order to remove from deliveries
   * @return true if the driver is scheduled to deliver the item,
   * 			false otherwise
   */
  public boolean removeOrder(PurchasedItem item) {
    for (int i = 0; i < items.length; i++) {
      if (items[i] == item) {
        items[i] = null;
        numItems--;
        return true;
      }
    }
    return false;
  }

  /**
   * Calculates the amount of money earned by the driver
   * @return amount of money earned by the driver
   */
  public double getMoneyEarned() {
    double hoursDelivering = (double)(minutesDelivering) / 60;
    double money = wage * hoursDelivering;
    if (hoursDelivering <= 8.00) {
      return money;
    } else {
      money += wage * (hoursDelivering - 8) * 1.50;
      return money;
    }
  }

  /**
   * Compares if the input object is equal to the instance
   * Two objects are equal if they are of the same type and
   * all instance variables are equal.
   * @return true if they are, false if they are not
   */
  @Override
  public boolean equals(Object obj) {
    if (obj == null || !(obj instanceof DeliveryDriver)) {
      return false;
    }
    DeliveryDriver driver = (DeliveryDriver) obj;
    if (!(this.getName().equals(driver.getName())) ||
            !((Math.round(this.getWage() * 100.00) / 100.00) == (Math.round(driver.getWage() * 100.00) / 100.00)) ||
            !(this.getTimeSpent() == driver.getTimeSpent()) ||
            !(this.getNumOrders() == driver.getNumOrders())) {
      return false;
    }
    return true;
  }
  @Override
  public String toString() {
    StringBuilder ret = new StringBuilder();

    ret.append("Name: ");
    ret.append(this.name);

    ret.append(" - Wage: $");
    ret.append(String.format("%.2f", this.wage));

    ret.append(" - Can Carry: ");
    ret.append(this.maxCarriableItems);

    ret.append(" items - Num Deliveries: ");
    ret.append(this.numDeliveries);

    ret.append(" - Minutes Worked: ");
    ret.append(this.minutesDelivering);
    ret.append(" min");

    ret.append(" - Currently Carrying: ");
    ret.append(this.numItems);
    ret.append(" items");

    return ret.toString();
  }

}
