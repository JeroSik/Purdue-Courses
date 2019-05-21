public class SideOrder implements PurchasedItem {
  private String customerName;
  private double materialCost;
  private double salesPrice;
  private int deliveryTime;
  private OrderSize size;

  public SideOrder(String name, double matCost, double sellPrice, int delTime, OrderSize size) {
    customerName = name;
    materialCost = matCost;
    salesPrice = sellPrice;
    deliveryTime = delTime;
    this.size = size;
  }
  public SideOrder(String name, double matCost, double sellPrice, int delTime) {
    this(name, matCost, sellPrice, delTime, OrderSize.SMALL);
  }
  public SideOrder(String name, double matCost, double sellPrice) {
    this(name, matCost, sellPrice, 0, OrderSize.SMALL);
  }

  public OrderSize getOrderSize() {
    return size;
  }
  public void setOrderSize(OrderSize size) {
    this.size = size;
  }

  public boolean isDelivery(){
    return deliveryTime > 0 && deliveryTime <= 30;
  }
  public String getCustomerName(){
    return customerName;
  }
  public int getDeliveryTime(){
    return deliveryTime;
  }
  public void setDeliveryTime(int time){
    if (time < 0) {
      deliveryTime = 0;
    } else {
      deliveryTime = time;
    }
  }
  public double getMaterialCost(){
    switch (size) {
      case SMALL:
        return materialCost + 0.00;
      case MEDIUM:
        return materialCost + 0.40;
      case LARGE:
        return materialCost + 0.80;
      case ABSURD:
        return materialCost + 1.50;
    }
    return materialCost;
  }
  public double getSalePrice(){
    switch (size) {
      case SMALL:
        return salesPrice + 0.00;
      case MEDIUM:
        return salesPrice + 2.00;
      case LARGE:
        return salesPrice + 3.00;
      case ABSURD:
        return salesPrice + 4.50;
    }
    return salesPrice;
  }

  public boolean equals(Object obj) {
    if (obj == null || !(obj instanceof SideOrder)) {
      return false;
    }
    SideOrder sideOrder = (SideOrder) obj;
    if (!(this.getCustomerName().equals(sideOrder.getCustomerName())) ||
            !((Math.round(this.getMaterialCost() * 100.00) / 100.00) == (Math.round(sideOrder.getMaterialCost() * 100.00) / 100.00)) ||
            !((Math.round(this.getSalePrice() * 100.00) / 100.00) == (Math.round(sideOrder.getSalePrice() * 100.00) / 100.00)) ||
            !(this.getDeliveryTime() == sideOrder.getDeliveryTime()) ||
            !(this.getOrderSize().equals(sideOrder.getOrderSize()))) {
      return false;
    }
    return true;
  }
}
