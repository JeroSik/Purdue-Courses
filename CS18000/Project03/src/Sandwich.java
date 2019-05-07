public class Sandwich implements PurchasedItem {
  public static double costOfCondiment = 0.05;
  public static double pricePerCondiment = 0.75;
  private String customerName;
  private double materialCost;
  private double salesPrice;
  private int deliveryTime;
  private Spicyness spiceLevel;
  private int numCondiments;

  public Sandwich(String name, double matCost, double sellPrice, int delTime, Spicyness level, int condiments) {
    customerName = name;
    materialCost = matCost;
    salesPrice = sellPrice;
    deliveryTime = delTime;
    spiceLevel = level;
    numCondiments = condiments;
  }
  public Sandwich(String name, double matCost, double sellPrice) {
    this(name, matCost, sellPrice,0, Spicyness.MILD, 0);
  }
  public Sandwich(String name, double matCost) {
    this(name, matCost, 3.5 * matCost, 0, Spicyness.MILD, 0);
  }

  public Spicyness getSpicyness() {
    return spiceLevel;
  }
  public void setSpicyness(Spicyness level) {
    spiceLevel = level;
  }

  public void addCondiments(int num) {
    numCondiments += num;
  }
  public void removeCondiments(int num) {
    if (num >= numCondiments) {
      numCondiments = 0;
    } else {
      numCondiments -= num;
    }
  }
  public int getNumCondiments() {
    return numCondiments;
  }

  @Override
  public boolean isDelivery() {
    return deliveryTime > 0 && deliveryTime <= 60;
  }
  @Override
  public String getCustomerName() {
    return customerName;
  }
  @Override
  public int getDeliveryTime() {
    return deliveryTime;
  }
  @Override
  public void setDeliveryTime(int time) {
    if (time < 0) {
      deliveryTime = 0;
    } else {
      deliveryTime = time;
    }
  }
  @Override
  public double getMaterialCost() {
    return materialCost + numCondiments * costOfCondiment;
  }
  @Override
  public double getSalePrice() {
    return salesPrice + numCondiments * pricePerCondiment;
  }

  public boolean equals(Object obj) {
    if (obj == null || !(obj instanceof Sandwich)) {
      return false;
    }
    Sandwich sandwich = (Sandwich) obj;
    if (!(this.getCustomerName().equals(sandwich.getCustomerName())) ||
            !((Math.round(this.getMaterialCost() * 100.00) / 100.00) == (Math.round(sandwich.getMaterialCost() * 100.00) / 100.00)) ||
            !((Math.round(this.getSalePrice() * 100.00) / 100.00) == (Math.round(sandwich.getSalePrice() * 100.00) / 100.00)) ||
            !(this.getDeliveryTime() == sandwich.getDeliveryTime()) ||
            !(this.getSpicyness().equals(sandwich.getSpicyness()))) {
      return false;
    }
    return true;
  }
}

