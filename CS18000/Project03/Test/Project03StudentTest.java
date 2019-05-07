//import net.sf.webcat.annotations.*; // for webcat
import org.junit.*;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

/*
        These test cases are organized by classes.
        We recommend you to comment out sections of these test cases and
        run them in an incremental way to test your code, class by class.
 */

public class Project03StudentTest {

  /***************************************************************
   *
   *  SANDWICH 15% + SPICYNESS 1% TESTS  : 22 tests
   *
   *  ************************************************************/

  @Test(timeout = 100)
  // (0.0075)
  public void testSandwichFullConstructorAndGetters() {
    String expectedName = "name";
    double initMatCost = 5;
    double initSellPrice = 25;
    int expectedDelTime = 30;
    Spicyness expectedSpiceLevel = Spicyness.HOT;
    int expectedCondiments = 1;

    Sandwich sandwich = new Sandwich(expectedName, initMatCost, initSellPrice,
            expectedDelTime, expectedSpiceLevel, expectedCondiments);


    String messageName = "Sandwich(): check if name has been initialized in the constructor";
    String messageDelTime = "Sandwich(): check if delTime has been initialized in the constructor";
    String messageSpiceLevel = "Sandwich(): check if spiceLevel has been initialized in the constructor\n" +
            "Sandwich::getSpicyness(): check that getter returns correct value";
    String messageCondiments = "Sandwich(): check if numCondiments has been initialized in the constructor\n" +
            "Sandwich::getNumCondiments(): check that getter returns correct value";

    assertEquals(messageName, expectedName, sandwich.getCustomerName());
    assertEquals(messageDelTime, expectedDelTime, sandwich.getDeliveryTime());
    assertEquals(messageSpiceLevel, expectedSpiceLevel, sandwich.getSpicyness());
    assertEquals(messageCondiments, expectedCondiments, sandwich.getNumCondiments());

  }

  @Test(timeout = 100)
  // (0.0075)
  public void testSandwichChainConstructor() {
    String expectedName = "name";
    double expectedMatCost = 5;
    double expectedSellPrice = 25;
    // expected default values
    int expectedDelTime = 0;
    Spicyness expectedSpiceLevel = Spicyness.MILD;
    int expectedCondiments = 0;

    Sandwich sandwich = new Sandwich(expectedName, expectedMatCost, expectedSellPrice);

    String messageName = "Sandwich(): check if name has been initialized in the constructor";
    // defaults
    String messageDelTime = "Sandwich(): check if default value for delTime has been correctly assigned in chain constructor";
    String messageSpiceLevel = "Sandwich(): check if default value for spiceLevel has been correctly assigned in chain constructor";
    String messageCondiments = "Sandwich(): check if default value for condiments has been correctly assigned in chain constructor";


    assertEquals(messageName, expectedName, sandwich.getCustomerName());
    assertEquals(messageDelTime, expectedDelTime, sandwich.getDeliveryTime());
    assertEquals(messageSpiceLevel, expectedSpiceLevel, sandwich.getSpicyness());
    assertEquals(messageCondiments, expectedCondiments, sandwich.getNumCondiments());
  }

  @Test(timeout = 100)
  // (0.015)
  public void testSandwichMaterialCost() {
    double delta = 0.001;
    double initMatCost = 5;
    double expectedMatCost = 5.05;
    String messageMatCost = "Sandwich::getMaterialCost(): check that getter returns correct value when an initial value was specified.";

    Sandwich sandwich = new Sandwich("name", initMatCost, 5, 20, Spicyness.HOT, 1);
    assertEquals(messageMatCost, expectedMatCost, sandwich.getMaterialCost(), delta);
  }

  @Test(timeout = 100)
  // (0.015)
  public void testSandwichSellPrice() {
    double delta = 0.001;
    double initSellPrice = 25;
    double expectedSellPrice = 25.75;
    String messageSellPrice = "Sandwich::getSalePrice(): check that getter returns correct value when an initial value was specified.";;

    Sandwich sandwich = new Sandwich("name", 5, initSellPrice, 20, Spicyness.HOT, 1);
    assertEquals(messageSellPrice, expectedSellPrice, sandwich.getSalePrice(), delta);
  }


  @Test(timeout = 100)
  // (0.015)
  public void testSandwichSellPriceDefault() {
    double delta = 0.001;
    double initMatCost = 5;
    double expectedSellPrice = 17.5;
    String messageSellPrice = "Sandwich::getSalePrice(): check that getter returns correct value when initial value was NOT specified (check defaults).";;

    Sandwich sandwich = new Sandwich("name", initMatCost);
    assertEquals(messageSellPrice, expectedSellPrice, sandwich.getSalePrice(), delta);
  }



  @Test(timeout = 100)
  // (0.006)
  public void testSandwichIsDeliveryTrueInRange() {
    Sandwich globalSandwich = new Sandwich("Customer", 100, 200);
    globalSandwich.setDeliveryTime(2);
    String message = "Sandwich::isDelivery(): check case where the time is within the accepted range";
    assertEquals(message, true, globalSandwich.isDelivery());

  }

  @Test(timeout = 100)
  // (0.006)
  public void testSandwichIsDeliveryFalseNegative() {
    Sandwich globalSandwich = new Sandwich("Customer", 100, 200);
    globalSandwich.setDeliveryTime(-10);
    String message = "Sandwich::isDelivery(): check case when time is NOT within the accepted range";
    assertEquals(message, false, globalSandwich.isDelivery());
  }

  @Test(timeout = 100)
  // (0.006)
  public void testSandwichIsDeliveryFalsePositive() {
    Sandwich globalSandwich = new Sandwich("Customer", 100, 200);
    globalSandwich.setDeliveryTime(70);
    String message = "Sandwich::isDelivery(): check case when time is NOT within the accepted range";
    assertEquals(message, false, globalSandwich.isDelivery());
  }

  @Test(timeout = 100)
  // (0.006)
  public void testSandwichIsDeliveryLowerBorderCase() {
    Sandwich globalSandwich = new Sandwich("Customer", 100, 200);
    globalSandwich.setDeliveryTime(0);
    String message = "Sandwich::isDelivery(): check case when time is NOT within the accepted range.";
    assertEquals(message, false, globalSandwich.isDelivery());
  }

  @Test(timeout = 100)
  // (0.006)
  public void testSandwichIsDeliveryUpperBorderCase() {
    Sandwich globalSandwich = new Sandwich("Customer", 100, 200);
    globalSandwich.setDeliveryTime(60);
    String message = "Sandwich::isDelivery(): check case when time is within the accepted range";
    assertEquals(message, true, globalSandwich.isDelivery());

  }

  @Test(timeout = 100)
  // (0.00375)
  public void testSandwichSetDeliveryTimePositive() {
    Sandwich globalSandwich = new Sandwich("Customer", 100, 200);
    globalSandwich.setDeliveryTime(30);
    String message = "Sandwich::setDeliveryTime(): check that delivery time is correctly updated";
    assertEquals(message, 30, globalSandwich.getDeliveryTime());
  }

  @Test(timeout = 100)
  // (0.00375)
  public void testSandwichSetDeliveryTimeNegative() {
    Sandwich globalSandwich = new Sandwich("Customer", 100, 200);
    globalSandwich.setDeliveryTime(-10);
    String message = "Sandwich::setDeliveryTime(): Minutes can't be set to negative. Set invalid inputs to 0";
    assertEquals(message, 0, globalSandwich.getDeliveryTime());
  }

  @Test(timeout = 100)
  // (0.0175)
  public void testSandwichSetSpicynessValidValue() {
    Sandwich globalSandwich = new Sandwich("Customer", 100, 200);
    String message = "Sandwich::setSpicyness(): check that it sets the correct Spicyness value.";
    String message2 = "Check correct implementation of Spicyness enum type";

    try {
      globalSandwich.setSpicyness(Spicyness.MILD);
      assertEquals(message, Spicyness.MILD, globalSandwich.getSpicyness()) ;

      globalSandwich.setSpicyness(Spicyness.MEDIUM);
      assertEquals(message, Spicyness.MEDIUM, globalSandwich.getSpicyness());

      globalSandwich.setSpicyness(Spicyness.HOT);
      assertEquals(message, Spicyness.HOT, globalSandwich.getSpicyness());

      globalSandwich.setSpicyness(Spicyness.CRAZY);
      assertEquals(message, Spicyness.CRAZY, globalSandwich.getSpicyness());

      globalSandwich.setSpicyness(Spicyness.INSANE);
      assertEquals(message, Spicyness.INSANE, globalSandwich.getSpicyness());
    } catch (Exception e) {
      e.printStackTrace();
      fail(message2);
    }
  }

  @Test(timeout = 100)
  // (0.0075)
  public void testSandwichAddCondiments() {
    Sandwich localSandwich = new Sandwich("localCustomer", 100, 200);
    String message = "Sandwich::addCondiments(): check that specified number of condiments are correctly added";
    localSandwich.addCondiments(6);
    assertEquals(message, 6, localSandwich.getNumCondiments());
  }

  @Test(timeout = 100)
  // (0.00375)
  public void testSandwichRemoveCondiments() {
    Sandwich localSandwich = new Sandwich("local", 10, 20);
    String message = "Sandwich::removeCondiments(): check that specified number of condiments are correctly removed";
    localSandwich.addCondiments(7);
    localSandwich.removeCondiments(5);
    assertEquals(message, 2, localSandwich.getNumCondiments());
  }

  @Test(timeout = 100)
  // (0.00375)
  public void testSandwichRemoveTooManyCondiments() {
    Sandwich localSandwich = new Sandwich("local", 10, 20);
    String message = "Sandwich::removeCondiments(): check attempt to remove more condiments than existent";
    localSandwich.addCondiments(5);
    localSandwich.removeCondiments(9);
    assertEquals(message, 0, localSandwich.getNumCondiments());
  }

  @Test(timeout = 100)
  // (0.00375)
  public void testSandwichEqualsTrue() {
    String message = "Sandwich::equals(): check the case where two sandwiches are equal";
    Sandwich sand1 = new Sandwich("sandwich", 2, 10);
    Sandwich sand2 = new Sandwich("sandwich", 2, 10);
    assertEquals(message, true, sand1.equals(sand2));
  }

  @Test(timeout = 100)
  // (0.00375)
  public void testSandwichEqualsFalseCustomerName() {
    String message = "Sandwich::equals(): check the case where two sandwiches have some different argument";
    Sandwich sand1 = new Sandwich("one", 2, 10);
    Sandwich sand2 = new Sandwich("on", 2, 10);
    assertEquals(message, false, sand1.equals(sand2));
  }

  @Test(timeout = 100)
  // (0.00375)
  public void testSandwichEqualsFalseMaterialCost() {
    String message = "Sandwich::equals(): check the case where two sandwiches have some different argument";
    Sandwich sand1 = new Sandwich("one", 2, 10);
    Sandwich sand2 = new Sandwich("one", 3, 10);
    assertEquals(message, false, sand1.equals(sand2));
  }

  @Test(timeout = 100)
  // (0.00375)
  public void testSandwichEqualsFalseSalesPrice() {
    String message = "Sandwich::equals(): check the case where two sandwiches have some different argument";
    Sandwich sand1 = new Sandwich("one", 2, 5);
    Sandwich sand2 = new Sandwich("one", 2, 7);
    assertEquals(message, false, sand1.equals(sand2));
  }

  @Test(timeout = 100)
  // (0.00375)
  public void testSandwichEqualsFalseDeliveryTime() {
    String message = "Sandwich::equals(): check the case where two sandwiches have some different argument";
    Sandwich sand1 = new Sandwich("one", 2, 10);
    Sandwich sand2 = new Sandwich("one", 2, 10);
    sand1.setDeliveryTime(1);
    sand2.setDeliveryTime(2);
    assertEquals(message, false, sand1.equals(sand2));
  }

  @Test(timeout = 100)
  // (0.00375)
  public void testSandwichEqualsFalseSpiceLevel() {
    String message = "Sandwich::equals(): check the case where two sandwiches have some different argument";
    Sandwich sand1 = new Sandwich("one", 2, 10);
    Sandwich sand2 = new Sandwich("one", 2, 10);
    sand1.setSpicyness(Spicyness.HOT);
    sand2.setSpicyness(Spicyness.CRAZY);
    assertEquals(message, false, sand1.equals(sand2));
  }

  @Test(timeout = 100)
  // (0.00375)
  public void testSandwichEqualsFalseNotInstanceOf() {
    String message = "Sandwich:equals(): check that the object is an instance of Sandwich";

    Sandwich sand = new Sandwich("one", 2, 10);
    Object other = new Object();
    try {
      assertEquals(message, false, sand.equals(other));
    } catch (Exception e) {
      e.printStackTrace();
      fail(message);
    }

  }

  @Test(timeout = 100)
  // (0.00375)
  public void testSandwichEqualsFalseNull() {
    String message = "Sandwich:equals(): check that the object is not null";

    Sandwich sand = new Sandwich("name", 2, 10);
    try {
      assertEquals(message, false, sand.equals(null));
    } catch (Exception e) {
      e.printStackTrace();
      fail(message);
    }

  }

  /***************************************************************
   *
   *  SIDE ORDER 15% TESTS + ORDER SIZE 1% :  25 tests
   *
   *  ************************************************************/
  @Test(timeout = 100)
  // (0.015)
  public void testSideOrderFullConstructor() {
    double delta = 0.001;

    String expectedName = "one";
    double expectedMatCost = 50;
    double expectedSellPrice = 100;
    int expectedDelTime = 30;
    OrderSize expectedOrderSize = OrderSize.SMALL;

    SideOrder order = new SideOrder(expectedName, expectedMatCost, expectedSellPrice,
            expectedDelTime, expectedOrderSize);

    String messageName = "SideOrder(): check if name has been initialized in the constructor";
    String messageMatCost = "SideOrder(): check if matCost has been initialized in the constructor";
    String messageSellPrice = "SideOrder(): check if sellPrice has been initialized in the constructor, uses default price";
    String messageDelTime =  "SideOrder(): check if delTime has been initialized in the constructor";
    String messageOrderSize = "SideOrder(): check if delTime has been initialized in the constructor";
    String defaultM = ". And check that getter returns the correct cost/price for SMALL orders";

    assertEquals(messageName, expectedName, order.getCustomerName());
    assertEquals(messageMatCost + defaultM, expectedMatCost, order.getMaterialCost(), delta);
    assertEquals(messageSellPrice + defaultM, expectedSellPrice, order.getSalePrice(), delta);
    assertEquals(messageDelTime, expectedDelTime, order.getDeliveryTime());
    assertEquals(messageOrderSize, expectedOrderSize, order.getOrderSize());

  }

  @Test(timeout = 100)
  // (0.0075)
  public void testSideOrderChainConstructor1() {
    double delta = 0.001;

    String expectedName = "one";
    double expectedMatCost = 50;
    double expectedSellPrice = 100;
    int expectedDelTime = 30;
    // default
    OrderSize expectedOrderSize = OrderSize.SMALL;

    SideOrder order = new SideOrder(expectedName, expectedMatCost, expectedSellPrice,
            expectedDelTime);

    String messageName = "SideOrder(): check if name has been initialized in the constructor";
    String messageMatCost = "SideOrder(): check if matCost has been initialized in the constructor";
    String messageSellPrice = "SideOrder(): check if sellPrice has been initialized in the constructor";
    String messageDelTime =  "SideOrder(): check if delTime has been initialized in the constructor";
    // defaults
    String messageOrderSize = "SideOrder(): check if default value for delTime has been initialized in the constructor";
    String defaultM = ". And check that it returns the base cost/price for a SMALL size order";

    assertEquals(messageName, expectedName, order.getCustomerName());
    assertEquals(messageMatCost + defaultM, expectedMatCost, order.getMaterialCost(), delta);
    assertEquals(messageSellPrice + defaultM, expectedSellPrice, order.getSalePrice(), delta);
    assertEquals(messageDelTime, expectedDelTime, order.getDeliveryTime());
    assertEquals(messageOrderSize, expectedOrderSize, order.getOrderSize());
  }

  @Test(timeout = 100)
  // (0.0075)
  public void testSideOrderChainConstructor2() {
    double delta = 0.001;

    String expectedName = "name";
    double expectedMatCost = 50;
    double expectedSellPrice = 100;
    // default
    int expectedDelTime = 0;
    OrderSize expectedOrderSize = OrderSize.SMALL;

    SideOrder order = new SideOrder(expectedName, expectedMatCost, expectedSellPrice);

    String messageName = "SideOrder(): check if name has been initialized in the constructor";
    String messageMatCost = "SideOrder(): check if matCost has been initialized in the constructor";
    String messageSellPrice = "SideOrder(): check if sellPrice has been initialized in the constructor";
    // default
    String messageDelTime = "SideOrder(): check if default value for delTime has been initialized in the constructor";
    String messageOrderSize = "SideOrder(): check if default value for delTime has been initialized in the constructor";
    String defaultM = ". And check that it returns the base cost/price for a SMALL size order";

    assertEquals(messageName, expectedName, order.getCustomerName());
    assertEquals(messageMatCost + defaultM, expectedMatCost, order.getMaterialCost(), delta);
    assertEquals(messageSellPrice + defaultM, expectedSellPrice, order.getSalePrice(), delta);
    assertEquals(messageDelTime, expectedDelTime, order.getDeliveryTime());
    assertEquals(messageOrderSize, expectedOrderSize, order.getOrderSize());
  }

  @Test(timeout = 100)
  // (0.006)
  public void testSideOrderIsDeliveryTrue() {
    SideOrder globalSideOrder = new SideOrder("name", 100, 200);
    globalSideOrder.setDeliveryTime(5);
    String message = "SideOrder::isDelivery(): check case where the time is within the accepted range";
    assertEquals(message, true, globalSideOrder.isDelivery());
  }

  @Test(timeout = 100)
  // (0.006)
  public void testSideOrderIsDeliveryFalseNegative() {
    SideOrder globalSideOrder = new SideOrder("name", 100, 200);
    globalSideOrder.setDeliveryTime(-10);
    String message = "SideOrder::isDelivery(): check case when time is NOT within the accepted range";
    assertEquals(message, false, globalSideOrder.isDelivery());
  }

  @Test(timeout = 100)
  // (0.006)
  public void testSideOrderIsDeliveryFalsePositive() {
    SideOrder globalSideOrder = new SideOrder("name", 100, 200);
    globalSideOrder.setDeliveryTime(41);
    String message = "SideOrder::isDelivery(): check case when time is NOT within the accepted range";
    assertEquals(message, false, globalSideOrder.isDelivery());
  }

  @Test(timeout = 100)
  // (0.006)
  public void testSideOrderIsDeliveryLowerBorderCase() {
    SideOrder globalSideOrder = new SideOrder("name", 100, 200);
    globalSideOrder.setDeliveryTime(0);
    String message = "SideOrder::isDelivery(): check case when time is NOT within the accepted range.";
    assertEquals(message, false, globalSideOrder.isDelivery());
  }

  @Test(timeout = 100)
  // (0.006)
  public void testSideOrderIsDeliveryUpperBorderCase() {
    SideOrder globalSideOrder = new SideOrder("name", 100, 200);
    globalSideOrder.setDeliveryTime(30);
    String message = "SideOrder::isDelivery(): check case when time is within the accepted range";
    assertEquals(message, true, globalSideOrder.isDelivery());

  }

  @Test(timeout = 100)
  // (0.00375)
  public void testSideOrderSetDeliveryTimePositive() {
    SideOrder globalSideOrder = new SideOrder("name", 100, 200);
    globalSideOrder.setDeliveryTime(12);
    String message = "SideOrder::setDeliveryTime(): Minutes can't be set to negative. Set invalid inputs to 0";
    assertEquals(message, 12, globalSideOrder.getDeliveryTime());
  }

  @Test(timeout = 100)
  // (0.00375))
  public void testSideOrderSetDeliveryTimeNegative() {
    SideOrder globalSideOrder = new SideOrder("name", 100, 200);
    globalSideOrder.setDeliveryTime(-10);
    String message = "SideOrder::setDeliveryTime(): Minutes can't be set to negative. Set invalid inputs to 0";
    assertEquals(message, 0, globalSideOrder.getDeliveryTime());
  }


  @Test(timeout = 100)
  // (0.0175)
  public void testSideOrderSetOrderSizeValidValue() {
    SideOrder globalSideOrder = new SideOrder("name", 100, 200);
    String message = "SideOrder::setOrderSize(): check that it sets the correct OrderSize value.";
    String message2 = "Spicyness: check your implementation of OrderSize enum";

    try {
      globalSideOrder.setOrderSize(OrderSize.SMALL);
      assertEquals(message, OrderSize.SMALL, globalSideOrder.getOrderSize());

      globalSideOrder.setOrderSize(OrderSize.MEDIUM);
      assertEquals(message, OrderSize.MEDIUM, globalSideOrder.getOrderSize());

      globalSideOrder.setOrderSize(OrderSize.LARGE);
      assertEquals(message, OrderSize.LARGE, globalSideOrder.getOrderSize());

      globalSideOrder.setOrderSize(OrderSize.ABSURD);
      assertEquals(message, OrderSize.ABSURD, globalSideOrder.getOrderSize());
    } catch (Exception e) {
      e.printStackTrace();
      fail(message2);
    }
  }

  @Test(timeout = 100)
  // (0.0075)
  public void testSideOrderGetMaterialCostAbsurd() {
    double delta = 0.001;

    String message = "SideOrder::getMaterialCost(): check the case where the size is ABSURD";
    SideOrder order = new SideOrder("one", 2, 10);
    order.setOrderSize(OrderSize.ABSURD);
    double expectedMatCost = 3.5;
    assertEquals(message, expectedMatCost, order.getMaterialCost(), delta);
  }

  @Test(timeout = 100)
  // (0.0075)
  public void testSideOrderGetMaterialCostLarge() {
    double delta = 0.001;

    String message = "SideOrder::getMaterialCost(): check the case where the size is LARGE";
    SideOrder order = new SideOrder("one", 2, 10);
    order.setOrderSize(OrderSize.LARGE);
    double expectedMatCost = 2.8;
    assertEquals(message, expectedMatCost, order.getMaterialCost(), delta);
  }

  @Test(timeout = 100)
  // (0.0075)
  public void testSideOrderGetMaterialCostMedium() {
    double delta = 0.001;

    String message = "SideOrder::getMaterialCost(): check the case where the size is MEDIUM";
    SideOrder order = new SideOrder("one", 2, 10);
    order.setOrderSize(OrderSize.MEDIUM);
    double expectedMatCost = 2.4;
    assertEquals(message, expectedMatCost, order.getMaterialCost(), delta);
  }

  @Test(timeout = 100)
  // (0.0075)
  public void testSideOrderGetSalePriceAbsurd() {
    double delta = 0.001;

    String message = "SideOrder::getSalePrice(): check the case where the size is ABSURD";
    SideOrder order = new SideOrder("one", 2, 10);
    order.setOrderSize(OrderSize.ABSURD);
    double expectedSalePrice = 14.5;
    assertEquals(message, expectedSalePrice, order.getSalePrice(), delta);
  }

  @Test(timeout = 100)
  // (0.0075)
  public void testSideOrderGetSalePriceLarge() {
    double delta = 0.001;

    String message = "SideOrder::getSalePrice(): check the case where the size is LARGE";
    SideOrder order = new SideOrder("one", 2, 10);
    order.setOrderSize(OrderSize.LARGE);
    double expectedSalePrice = 13;
    assertEquals(message, expectedSalePrice, order.getSalePrice(), delta);
  }

  @Test(timeout = 100)
  // (0.0075)
  public void testSideOrderGetSalePriceMedium() {
    double delta = 0.001;

    String message = "SideOrder::getSalePrice(): check the case where the size is MEDIUM";
    SideOrder order = new SideOrder("one", 2, 10);
    order.setOrderSize(OrderSize.MEDIUM);
    double expectedSalePrice = 12;
    assertEquals(message, expectedSalePrice, order.getSalePrice(), delta);
  }

  @Test(timeout = 100)
  // (0.00375)
  public void testSideOrderEqualsTrue() {
    String message = "SideOrder::equals(): check the case where two SideOrders are equal";
    SideOrder order1 = new SideOrder("one", 2, 10);
    SideOrder order2 = new SideOrder("one", 2, 10);
    assertEquals(message, true, order1.equals(order2));
  }

  @Test(timeout = 100)
  // (0.00375)
  public void testSideOrderEqualsFalseCustomerName() {
    String message = "SizeOrder::equals(): check the case where two SideOrders have some different argument";
    SideOrder order1 = new SideOrder("one", 2, 10);
    SideOrder order2 = new SideOrder("eno", 2, 10);
    assertEquals(message, false, order1.equals(order2));
  }

  @Test(timeout = 100)
  // (0.00375)
  public void testSideOrderEqualsFalseMaterialCost() {
    String message = "SizeOrder::equals(): check the case where two SideOrders have some different argument";
    SideOrder order1 = new SideOrder("one", 2, 10);
    SideOrder order2 = new SideOrder("one", 3, 10);
    assertEquals(message, false, order1.equals(order2));
  }

  @Test(timeout = 100)
  // (0.00375)
  public void testSideOrderEqualsFalseSalesPrice() {
    String message = "SizeOrder::equals(): check the case where two SideOrders have some different argument";
    SideOrder order1 = new SideOrder("one", 1, 2);
    SideOrder order2 = new SideOrder("one", 1, 3);
    assertEquals(message, false, order1.equals(order2));
  }

  @Test(timeout = 100)
  // (0.00375)
  public void testSideOrderEqualsFalseDeliveryTime() {
    String message = "SizeOrder::equals(): check the case where two SideOrders have some different argument";
    SideOrder order1 = new SideOrder("one", 2, 10);
    SideOrder order2 = new SideOrder("one", 2, 10);
    order1.setDeliveryTime(4);
    order2.setDeliveryTime(5);
    assertEquals(message, false, order1.equals(order2));
  }

  @Test(timeout = 100)
  // (0.00375)
  public void testSideOrderEqualsFalseSize() {
    String message = "SizeOrder::equals(): check the case where two SideOrders have some different argument";
    SideOrder order1 = new SideOrder("one", 2, 10);
    SideOrder order2 = new SideOrder("one", 2, 10);
    order1.setOrderSize(OrderSize.MEDIUM);
    order2.setOrderSize(OrderSize.LARGE);
    assertEquals(message, false, order1.equals(order2));
  }

  @Test(timeout = 100)
  // (0.00375)
  public void testSideOrderEqualsFalseNotInstanceOf() {
    String message = "SideOrder:equals(): check that the object is an instance of SideOrder";

    SideOrder order = new SideOrder("one", 2, 10);
    Object other = new Object();
    try {
      assertEquals(message, false, order.equals(other));
    } catch (Exception e) {
      e.printStackTrace();
      fail(message);
    }

  }

  @Test(timeout = 100)
  // (0.00375)
  public void testSideOrderEqualsFalseNull() {
    String message = "SideOrder:equals(): check that the object is not null";

    SideOrder order = new SideOrder("one", 2, 10);
    try {
      assertEquals(message, false, order.equals(null));
    } catch (Exception e) {
      e.printStackTrace();
      fail(message);
    }

  }

  /***************************************************************
   *
   *  DELIVERY DRIVER 32% TESTS  :  42 tests
   *
   *  ************************************************************/
  @Test(timeout = 100)
  // (0.015)
  public void testDeliveryDriverConstructor() {
    double delta = 0.001;
    String expectedName = "one";
    double expectedWage = 7.5;
    int expectedMaxCapacity = 1;

    DeliveryDriver driver = new DeliveryDriver(expectedName,
            expectedWage, expectedMaxCapacity);

    String messageName = "DeliveryDriver(): check that name has been correctly initialized in constructor";
    String messageWage = "DeliveryDriver(): check that wage has been correctly initialized in constructor";
    String messageMaxCapacity = "DeliveryDriver(): check that maximum capacity is correctly initialized in constructor";

    assertEquals(messageName, expectedName, driver.getName());
    assertEquals(messageWage, expectedWage, driver.getWage(), delta);
    assertEquals(messageMaxCapacity, expectedMaxCapacity, driver.getMaxCapacity());
  }

  @Test(timeout = 100)
  // (0.0075)
  public void testDeliveryDriverConstructorChain1() {
    double delta = 0.001;
    String expectedName = "one";
    double expectedWage = 7.5;
    int expectedMaxCapacity = 5;

    DeliveryDriver driver = new DeliveryDriver(expectedName,
            expectedWage);

    String messageName = "DeliveryDriver(): check that name has been correctly initialized in constructor";
    String messageWage = "DeliveryDriver(): check that wage has been correctly initialized in constructor";
    // default
    String messageMaxCapacity = "DeliveryDriver(): check that default maximum capacity is set if unspecified";

    assertEquals(messageName, expectedName, driver.getName());
    assertEquals(messageWage, expectedWage, driver.getWage(), delta);
    assertEquals(messageMaxCapacity, expectedMaxCapacity, driver.getMaxCapacity());
  }

  @Test(timeout = 100)
  // (0.0075)
  public void testDeliveryDriverConstructorChain2() {
    double delta = 0.001;
    String expectedName = "one";
    double expectedWage = 7.25;
    int expectedMaxCapacity = 5;

    DeliveryDriver driver = new DeliveryDriver(expectedName);

    String messageName = "DeliveryDriver(): check that name has been correctly initialized in constructor";
    // defaults
    String messageWage = "DeliveryDriver(): check that default wage is set if unspecified";
    String messageMaxCapacity = "DeliveryDriver(): check that default maximum capacity is set if unspecified";

    assertEquals(messageName, expectedName, driver.getName());
    assertEquals(messageWage, expectedWage, driver.getWage(), delta);
    assertEquals(messageMaxCapacity, expectedMaxCapacity, driver.getMaxCapacity());
  }

  @Test(timeout = 100)
  // (0.003)
  public void testPickupSandwichTrue() {
    String message = "DeliveryDriver::pickupOrder(): check the case where pickupOrder returns true";
    message += " and the item is a Sandwich";
    Sandwich sand = new Sandwich("one", 2, 10);
    DeliveryDriver driver = new DeliveryDriver("driver", 900, 10);
    sand.setDeliveryTime(12);
    assertEquals(message, true, driver.pickupOrder(sand));
  }

  @Test(timeout = 100)
  // (0.003)
  public void testPickupSandwichFalseNotDelivery() {
    String message = "DeliveryDriver::pickupOrder(): check the case where pickupOrder returns false when the item is not marked for delivery";
    message += " and the item is a Sandwich";
    Sandwich sand = new Sandwich("one", 2, 10);
    DeliveryDriver driver = new DeliveryDriver("driver", 740, 10);
    sand.setDeliveryTime(200);
    assertEquals(message, false, driver.pickupOrder(sand));
  }

  @Test(timeout = 100)
  // (0.003)
  public void testPickupFalseMaxCapacity() {
    String message = "DeliveryDriver::pickupOrder(): check the case where maximum capacity has been reached";
    Sandwich sand = new Sandwich("one", 2, 10);
    SideOrder ord = new SideOrder("two", 2, 10);
    DeliveryDriver driver = new DeliveryDriver("driver", 70, 2);
    sand.setDeliveryTime(10);
    ord.setDeliveryTime(10);
    driver.pickupOrder(sand);
    driver.pickupOrder(ord);
    assertEquals(message, false, driver.pickupOrder(sand));
  }

  @Test(timeout = 100)
  // (0.003)
  public void testPickupSideOrderTrue() {
    String message = "DeliveryDriver::pickupOrder(): check the case where pickupOrder returns true";
    message += " and the item is a SideOrder";
    SideOrder sand = new SideOrder("one", 2, 10);
    DeliveryDriver driver = new DeliveryDriver("driver", 70, 2);
    sand.setDeliveryTime(12);
    assertEquals(message, true, driver.pickupOrder(sand));
  }

  @Test(timeout = 100)
  // (0.003)
  public void testPickupSideOrderFalseNotDelivery() {
    String message = "DeliveryDriver::pickupOrder(): check the case where the item is not marked for delivery";
    message += " and the item is a SideOrder";
    SideOrder sand = new SideOrder("one", 2, 10);
    DeliveryDriver driver = new DeliveryDriver("driver", 70, 2);
    sand.setDeliveryTime(100);
    assertEquals(message, false, driver.pickupOrder(sand));
  }

  @Test(timeout = 100)
  //ScoringWeight(0.003)
  public void testRemoveSandwichFalseItemNotFound() {
    String message = "DeliveryDriver::removeOrder(): check the case where an item is not found.";
    Sandwich sand1 = new Sandwich("one", 2, 10);
    Sandwich sand2 = new Sandwich("two", 2, 10);
    DeliveryDriver driver = new DeliveryDriver("driver", 16, 2);
    sand1.setDeliveryTime(10);
    sand2.setDeliveryTime(10);
    driver.pickupOrder(sand1);
    assertEquals(message, false, driver.removeOrder(sand2));
  }

  @Test(timeout = 100)
  //ScoringWeight(0.003)
  public void testRemoveSideOrderFalseItemNotFound() {
    String message = "DeliveryDriver::removeOrder(): check the case where an item is not found.";
    SideOrder side = new SideOrder("one", 100, 200);
    SideOrder otherSide = new SideOrder("other", 100, 200);
    DeliveryDriver driver = new DeliveryDriver("name", 12, 20);
    side.setDeliveryTime(15);
    otherSide.setDeliveryTime(15);
    driver.pickupOrder(side);
    assertEquals(message, false, driver.removeOrder(otherSide));
  }

  @Test(timeout = 100)
  //ScoringWeight(0.003)
  public void testRemoveFalseNoItems() {
    String message = "DeliveryDriver:removeOrder(): check the case where there are no items";
    DeliveryDriver driver = new DeliveryDriver("name", 12, 5);
    SideOrder side = new SideOrder("side", 2, 10);
    Sandwich sand = new Sandwich("sand", 2, 10);
    side.setDeliveryTime(5);
    sand.setDeliveryTime(5);
    assertEquals(message, false, driver.removeOrder(side));
    assertEquals(message, false, driver.removeOrder(sand));
  }

  @Test(timeout = 100)
  //ScoringWeight(0.003)
  public void testRemoveSandwichTrue() {
    String message = "DeliveryDriver::removeOrder(): check return value when an item is found.";
    Sandwich sand = new Sandwich("name", 2, 10);
    DeliveryDriver driver = new DeliveryDriver("driver", 7, 10);
    sand.setDeliveryTime(12);
    driver.pickupOrder(sand);
    assertEquals(message, true, driver.removeOrder(sand));
  }

  @Test(timeout = 100)
  //ScoringWeight(0.003)
  public void testRemoveSideOrderTrue() {
    String message = "DeliveryDriver::removeOrder(): check return value when an item is found.";
    SideOrder sand = new SideOrder("name", 2, 10);
    DeliveryDriver driver = new DeliveryDriver("name", 7, 10);
    sand.setDeliveryTime(12);
    driver.pickupOrder(sand);
    assertEquals(message, true, driver.removeOrder(sand));
  }

  @Test(timeout = 100)
  // (0.0015)
  public void testNumOrdersInit() {
    String message = "DeliveryDriver::getNumOrders(): check the number of orders when no orders have been added";
    DeliveryDriver driver = new DeliveryDriver("name", 7.5, 3);
    assertEquals(message, 0, driver.getNumOrders());
  }

  @Test(timeout = 100)
  // (0.003)
  public void testNumOrdersAdd() {
    String message = "DeliveryDriver: check pickupOrder() to make sure the number of orders when a new order is picked up";
    DeliveryDriver driver = new DeliveryDriver("name", 7.5, 3);

    Sandwich sand = new Sandwich("one", 2, 10);
    SideOrder ord = new SideOrder("two", 2, 10);
    sand.setDeliveryTime(12);
    ord.setDeliveryTime(12);

    driver.pickupOrder(sand);
    assertEquals(message, 1, driver.getNumOrders());
    driver.pickupOrder(ord);
    assertEquals(message, 2, driver.getNumOrders());
  }

  @Test(timeout = 100)
  // (0.003)
  public void testNumOrdersPickUpFalseCapacity() {
    String message = "DeliveryDriver::pickUpOrder() check the number of orders in case of maximum capacity reached";
    DeliveryDriver driver = new DeliveryDriver("name", 7, 0);

    Sandwich sand = new Sandwich("name", 100, 200);
    SideOrder ord = new SideOrder("name", 100, 200);
    sand.setDeliveryTime(11);
    ord.setDeliveryTime(11);

    driver.pickupOrder(sand);
    assertEquals(message, 0, driver.getNumOrders());
    driver.pickupOrder(ord);
    assertEquals(message, 0, driver.getNumOrders());
  }

  @Test(timeout = 100)
  // (0.003)
  public void testNumOrdersPickUpFalseNotDelivery() {
    String message = "DeliveryDriver::pickUpOrder() check the number of orders when item is not marked for delivery";
    DeliveryDriver driver = new DeliveryDriver("name", 10, 2);

    Sandwich sand = new Sandwich("name", 100, 200);
    SideOrder ord = new SideOrder("name", 100, 200);
    sand.setDeliveryTime(100);
    ord.setDeliveryTime(100);

    driver.pickupOrder(sand);
    assertEquals(message, 0, driver.getNumOrders());
    driver.pickupOrder(ord);
    assertEquals(message, 0, driver.getNumOrders());
  }

  @Test(timeout = 100)
  // (0.003)
  public void testNumOrdersRemove() {
    String message = "DeliveryDriver::removeOrder() check that the number of orders is updated when an order is removed";
    DeliveryDriver driver = new DeliveryDriver("name", 7, 2);

    Sandwich sand = new Sandwich("one", 2, 10);
    SideOrder ord = new SideOrder("two", 2, 10);
    sand.setDeliveryTime(12);
    ord.setDeliveryTime(12);

    driver.pickupOrder(sand);
    driver.pickupOrder(ord);

    driver.removeOrder(sand);
    assertEquals(message, 1, driver.getNumOrders());

    driver.removeOrder(ord);
    assertEquals(message, 0, driver.getNumOrders());
  }

  @Test(timeout = 100)
  // (0.003)
  public void testNumOrdersRemoveOrderFalseItemNotFound() {
    String message = "DeliveryDriver::removeOrder() check the number of orders when the item is not on the list";
    DeliveryDriver driver = new DeliveryDriver("name", 15, 3);

    Sandwich sand = new Sandwich("1", 100, 200);
    SideOrder ord = new SideOrder("2", 100, 200);
    Sandwich otherSand = new Sandwich("3", 100, 200);
    SideOrder otherOrd = new SideOrder("4", 100, 200);

    otherSand.setDeliveryTime(12);
    otherOrd.setDeliveryTime(12);
    sand.setDeliveryTime(12);
    ord.setDeliveryTime(12);

    driver.pickupOrder(otherSand);
    driver.pickupOrder(otherOrd);

    driver.removeOrder(sand);
    assertEquals(message, 2, driver.getNumOrders());
    driver.removeOrder(ord);
    assertEquals(message, 2, driver.getNumOrders());


  }

  @Test(timeout = 100)
  // (0.0015)
  public void testArrayOrdersInit() {
    String message = "DeliveryDriver::pickUpOrder()/getOrders() check array when no orders have been added";
    DeliveryDriver driver = new DeliveryDriver("name", 15, 7);
    PurchasedItem[] expectedArray = {};
    assertArrayEquals(message, expectedArray, driver.getOrders());
  }

  @Test(timeout = 100)
  // (0.015)
  public void testArrayOrdersPickupOne() {
    String message = "DeliveryDriver::pickUpOrder()/getOrders()  check when items are added";
    DeliveryDriver driver = new DeliveryDriver("name", 15, 5);

    Sandwich sand = new Sandwich("name", 1, 2);
    sand.setDeliveryTime(5);

    driver.pickupOrder(sand);
    PurchasedItem[] expectedArray1 = {sand};
    assertArrayEquals(message, expectedArray1, driver.getOrders());

  }

  @Test(timeout = 100)
  // (0.015)
  public void testArrayOrdersPickupTwo() {
    String message = "DeliveryDriver::pickUpOrder()/getOrders() check when consecutive items are added";
    String message2 = "DeliveryDriver::pickUpOrder()/getOrders() check that resulting array has the correct size";
    DeliveryDriver driver = new DeliveryDriver("name", 15, 5);

    Sandwich sand = new Sandwich("one", 2, 10);
    SideOrder ord = new SideOrder("two", 3, 11);
    sand.setDeliveryTime(12);
    ord.setDeliveryTime(13);

    driver.pickupOrder(sand);
    driver.pickupOrder(ord);

    ArrayList<PurchasedItem> item = new ArrayList<>(Arrays.asList(driver.getOrders()));
    assertEquals(message, true, item.contains(sand));
    assertEquals(message, true, item.contains(ord));
    assertEquals(message2, 2, item.size());

  }

  @Test(timeout = 100)
  // (0.015)
  public void testArrayOrdersPickupThree() {
    String message = "DeliveryDriver::pickUpOrder()/getOrders() check when consecutive items are added";
    String message2 = "DeliveryDriver::pickUpOrder()/getOrders() check that resulting array has the correct size";
    DeliveryDriver driver = new DeliveryDriver("name", 15, 5);

    Sandwich sand = new Sandwich("name", 2, 10);
    Sandwich sand2 = new Sandwich("other", 3, 11);
    SideOrder ord = new SideOrder("name", 4, 12);
    sand.setDeliveryTime(15);
    ord.setDeliveryTime(16);
    sand2.setDeliveryTime(17);

    driver.pickupOrder(sand);
    driver.pickupOrder(ord);
    driver.pickupOrder(sand2);

    ArrayList<PurchasedItem> item = new ArrayList<>(Arrays.asList(driver.getOrders()));
    assertEquals(message, true, item.contains(sand));
    assertEquals(message, true, item.contains(ord));
    assertEquals(message, true, item.contains(sand2));
    assertEquals(message2, 3, item.size());

  }

  @Test(timeout = 100)
  // (0.003)
  public void testArrayOrdersPickUpFalseCapacity() {
    String message = "DeliveryDriver::pickUpOrder()/getOrders() check array when max capacity is reached";
    DeliveryDriver driver = new DeliveryDriver("name", 15, 0);

    Sandwich sand = new Sandwich("sand", 1, 2);
    SideOrder ord = new SideOrder("ord", 1, 2);
    sand.setDeliveryTime(2);
    ord.setDeliveryTime(3);

    driver.pickupOrder(sand);
    PurchasedItem[] expectedArray = {};
    assertArrayEquals(message,  expectedArray, driver.getOrders());
    driver.pickupOrder(ord);
    assertArrayEquals(message, expectedArray, driver.getOrders());
  }

  @Test(timeout = 100)
  // (0.003)
  public void testArrayOrdersPickUpFalseNotDelivery() {
    String message = "DeliveryDriver::pickUpOrder()/getOrders() check array when item is not marked for delivery";
    DeliveryDriver driver = new DeliveryDriver("name", 15, 2);

    Sandwich sand = new Sandwich("one", 11, 12);
    SideOrder ord = new SideOrder("two", 11, 14);
    sand.setDeliveryTime(100);
    ord.setDeliveryTime(100);

    driver.pickupOrder(sand);
    PurchasedItem[] expectedArray = {};
    assertArrayEquals(message, expectedArray, driver.getOrders());
    driver.pickupOrder(ord);
    assertArrayEquals(message, expectedArray, driver.getOrders());
  }

  @Test(timeout = 100)
  // (0.015)
  public void testArrayOrdersRemoveMiddle() {
    String message = "DeliveryDriver::removeOrder()/getOrders() check when items in the middle of the array are removed";
    String message2 = "DeliveryDriver::removeOrder()/getOrders() check that resulting array has the correct size";
    DeliveryDriver driver = new DeliveryDriver("name", 15, 4);

    Sandwich sand = new Sandwich("name", 1, 2);
    SideOrder ord = new SideOrder("chacha", 2, 3);
    Sandwich sand2 = new Sandwich("other", 3, 4);
    sand.setDeliveryTime(10);
    ord.setDeliveryTime(11);
    sand2.setDeliveryTime(12);

    driver.pickupOrder(sand);
    driver.pickupOrder(sand2);
    driver.pickupOrder(ord);

    driver.removeOrder(sand2);

    ArrayList<PurchasedItem> items = new ArrayList<>(Arrays.asList(driver.getOrders()));
    assertEquals(message, true, items.contains(sand));
    assertEquals(message, true, items.contains(ord));
    assertEquals(message, false, items.contains(sand2));
    assertEquals(message2, 2, items.size());
  }

  @Test(timeout = 100)
  // (0.015)
  public void testArrayOrdersRemoveFirst() {
    String message = "DeliveryDriver::removeOrder()/getOrders() check when items in the beginning of the array are removed";
    String message2 = "DeliveryDriver::removeOrder()/getOrders() check that resulting array has the correct size";
    DeliveryDriver driver = new DeliveryDriver("name", 15, 6);

    Sandwich sand = new Sandwich("name", 10, 20);
    SideOrder ord = new SideOrder("name", 15, 25);
    sand.setDeliveryTime(11);
    ord.setDeliveryTime(17);

    driver.pickupOrder(sand);
    driver.pickupOrder(ord);
    driver.removeOrder(sand);

    ArrayList<PurchasedItem> items = new ArrayList<>(Arrays.asList(driver.getOrders()));
    assertEquals(message, true, items.contains(ord));
    assertEquals(message2, 1, items.size());
  }

  @Test(timeout = 100)
  // (0.015)
  public void testArrayOrdersRemoveLast() {
    String message = "DeliveryDriver::removeOrder()/getOrders() check when items in the end of the array are removed";
    DeliveryDriver driver = new DeliveryDriver("name", 15, 1);

    SideOrder ord = new SideOrder("name", 10, 20);
    ord.setDeliveryTime(18);
    driver.pickupOrder(ord);
    driver.removeOrder(ord);
    ArrayList<PurchasedItem>  items = new ArrayList<>(Arrays.asList(driver.getOrders()));
    assertEquals(message, 0, items.size());

  }


  @Test(timeout = 100)
  // (0.003)
  public void testArrayOrdersRemoveOrderFalseItemNotFound() {
    String message = "DeliveryDriver::removeOrder()/getOrders() check when item doesn't exist";
    DeliveryDriver driver = new DeliveryDriver("name", 15, 3);

    Sandwich sand = new Sandwich("1", 100, 200);
    SideOrder ord = new SideOrder("2", 100, 200);
    Sandwich otherSand = new Sandwich("3", 100, 200);
    SideOrder otherOrd = new SideOrder("4", 100, 200);

    sand.setDeliveryTime(5);
    ord.setDeliveryTime(15);
    PurchasedItem[] expectedArray = {sand, ord};

    driver.pickupOrder(sand);
    driver.pickupOrder(ord);

    driver.removeOrder(otherSand);
    assertArrayEquals(message, expectedArray, driver.getOrders());
    driver.removeOrder(otherOrd);
    assertArrayEquals(message, expectedArray, driver.getOrders());

  }

  @Test(timeout = 100)
  // (0.003)
  public void testNumOrdersDeliverOrders() {
    String message = "DeliveryDriver::deliverOrders() check number of orders after delivery";
    DeliveryDriver driver = new DeliveryDriver("name", 15, 5);

    Sandwich sand = new Sandwich("1", 1, 2);
    SideOrder ord = new SideOrder("2", 2, 6);
    sand.setDeliveryTime(15);
    ord.setDeliveryTime(15);

    driver.pickupOrder(sand);
    driver.pickupOrder(ord);

    driver.deliverOrders();
    assertEquals(message, 0, driver.getNumOrders());
  }

  @Test(timeout = 100)
  // (0.015)
  public void testNumDeliveredDeliverOrders() {
    String message = "DeliveryDriver::deliverOrders() check number of deliveries after delivery";
    DeliveryDriver driver = new DeliveryDriver("name", 15, 7);

    for (int i = 0; i < 8; i++) {
      Sandwich sand = new Sandwich(Integer.toString(i), 100, 200);
      sand.setDeliveryTime(15);
      driver.pickupOrder(sand);
    }

    driver.deliverOrders();
    assertEquals(message, 7, driver.getNumDelivered());
  }

  @Test(timeout = 100)
  // (0.03)
  public void testArrayOrdersDeliverOrders() {
    String message = "DeliveryDriver::deliverOrders()/getOrders() check array after delivery";
    DeliveryDriver driver = new DeliveryDriver("name", 15, 5);

    Sandwich sand = new Sandwich("1", 100, 200);
    SideOrder ord = new SideOrder("2", 100, 200);
    sand.setDeliveryTime(11);
    ord.setDeliveryTime(11);

    driver.pickupOrder(sand);
    driver.pickupOrder(ord);

    driver.deliverOrders();
    PurchasedItem[] expectedArray = {};
    assertArrayEquals(message, expectedArray, driver.getOrders());
  }

  @Test(timeout = 100)
  // (0.015)
  public void testDeliverOrdersDeliveryTime() {
    String message = "DeliveryDriver::deliverOrders() check that time spent delivering the items";
    DeliveryDriver driver = new DeliveryDriver("name", 15, 5);

    Sandwich sand = new Sandwich("1", 100, 200);
    SideOrder ord = new SideOrder("2", 100, 200);

    sand.setDeliveryTime(7);
    driver.pickupOrder(sand);
    driver.deliverOrders();
    assertEquals(message, driver.getTimeSpent(), 7);

    ord.setDeliveryTime(10);
    driver.pickupOrder(ord);
    driver.deliverOrders();
    assertEquals(message, 17, driver.getTimeSpent());

  }

  @Test(timeout = 100)
  // (0.015)
  public void testGetMoneyEarnedWithoutOvertime() {
    double delta = 0.001;
    String message = "DeliveryDriver::getMoneyEarned(): check money earned by the driver with no overtime";
    DeliveryDriver driver = new DeliveryDriver("name", 7.5, 5);

    Sandwich sand = new Sandwich("1", 100, 200);
    sand.setDeliveryTime(6);
    driver.pickupOrder(sand);
    driver.deliverOrders();

    double expectedMoney = 0.75;
    assertEquals(message, expectedMoney, driver.getMoneyEarned(), delta);
  }

  @Test(timeout = 100)
  // (0.015)
  public void testGetMoneyEarnedWithOvertime() {
    double delta = 0.001;
    DeliveryDriver driver = new DeliveryDriver("name", 7.5, 10);
    String message = "DeliveryDriver::getMoneyEarned(): check money earned by the driver with overtime";

    for (int i = 0; i < 20; i++) {
      Sandwich sand = new Sandwich(Integer.toString(i), 100, 200);
      sand.setDeliveryTime(15);
      driver.pickupOrder(sand);
    }

    driver.deliverOrders();

    double expectedMoney = 18.75;
    assertEquals(message, expectedMoney, driver.getMoneyEarned(), delta);
  }

  @Test(timeout = 100)
  // (0.006)
  public void testDeliveryDriverEqualsTrue() {
    String message = "DeliveryDriver::equals(): check the case when drivers are equal";

    DeliveryDriver driver1 = new DeliveryDriver(new String("driver"), 4, 8);
    DeliveryDriver driver2 = new DeliveryDriver(new String("driver"), 4, 8);

    assertEquals(message, true, driver1.equals(driver2));

  }

  @Test(timeout = 100)
  // (0.003)
  public void testDeliveryDriverEqualsFalseName() {
    String message = "DeliveryDriver::equals(): check the case when drivers have some different argument";

    DeliveryDriver driver1 = new DeliveryDriver("name", 7, 20);
    DeliveryDriver driver2 = new DeliveryDriver("other", 7, 20);

    assertEquals(message, false, driver1.equals(driver2));

  }


  @Test(timeout = 100)
  // (0.003)
  public void testDeliveryDriverEqualsFalseWage() {
    String message = "DeliveryDriver::equals(): check the case when drivers have some different argument";

    DeliveryDriver driver1 = new DeliveryDriver("name", 1, 2);
    DeliveryDriver driver2 = new DeliveryDriver("name", 0, 2);

    assertEquals(message, false, driver1.equals(driver2));

  }


  @Test(timeout = 100)
  // (0.006)
  public void testDeliveryDriverEqualsFalseMinutes() {
    String message = "DeliveryDriver::equals(): check the case when drivers have some different argument";

    DeliveryDriver driver1 = new DeliveryDriver("name", 10, 2);
    DeliveryDriver driver2 = new DeliveryDriver("name", 10, 2);

    Sandwich sand = new Sandwich("1", 100, 200);
    Sandwich sand2 = new Sandwich("1", 100, 200);
    sand.setDeliveryTime(10);
    sand.setDeliveryTime(11);

    driver1.pickupOrder(sand);
    driver2.pickupOrder(sand2);

    driver1.deliverOrders();
    driver2.deliverOrders();

    assertEquals(message, false, driver1.equals(driver2));

  }

  @Test(timeout = 100)
  // (0.006)
  public void testDeliveryDriverEqualsFalseNumDeliveries() {
    String message = "DeliveryDriver::equals(): check the case when drivers have some different argument";

    DeliveryDriver driver1 = new DeliveryDriver("name", 10, 2);
    DeliveryDriver driver2 = new DeliveryDriver("name", 10, 2);

    Sandwich sand = new Sandwich("1", 100, 200);
    sand.setDeliveryTime(30);
    driver1.pickupOrder(sand);
    driver1.deliverOrders();

    assertEquals(message, false, driver1.equals(driver2));

  }

  @Test(timeout = 100)
  // (0.003)
  public void testDeliveryDriverEqualsFalseNotInstanceOf() {
    String message = "DeliveryDriver::equals(): check that the object is an instance of DeliveryDriver";

    DeliveryDriver driver1 = new DeliveryDriver("name", 30, 200);
    Object other = new Object();
    try {
      assertEquals(message, false, driver1.equals(other));
    } catch (Exception e) {
      e.printStackTrace();
      fail(message);
    }

  }

  @Test(timeout = 100)
  // (0.003)
  public void testDeliveryDriverEqualsFalseNull() {
    String message = "DeliveryDriver::equals(): check that the object is not null";

    DeliveryDriver driver1 = new DeliveryDriver("name", 30, 50);
    try {
      assertEquals(message, false, driver1.equals(null));
    } catch (Exception e) {
      e.printStackTrace();
      fail(message);
    }

  }

  /***************************************************************
   *
   *  STORE 33% TESTS  :   tests
   *
   *  ************************************************************/

  @Test(timeout = 100)
  // (0.0165)
  public void testConstructor1() {
    String expectedName = "name";
    int expectedNumDrivers = 3;

    String messageName = "Store(): check if storeName has been initialized in the constructor";
    String messageDrivers = "Store(): check if the array of drivers is initialized correctly";

    DeliveryDriver d0 = new DeliveryDriver("Driver0");
    DeliveryDriver d1 = new DeliveryDriver("Driver1");
    DeliveryDriver d2 = new DeliveryDriver("Driver2");
    DeliveryDriver[] expectedDrivers = {d0, d1, d2};

    Store store = new Store(expectedName, expectedNumDrivers);

    assertEquals(messageName, expectedName, store.getStoreName());
    assertArrayEquals(messageDrivers, expectedDrivers, store.getDrivers());

  }

  @Test(timeout = 100)
  // (0.0165)
  public void testConstructor2() {
    String expectedName = "name";

    String messageName = "Store(): check if storeName has been initialized in the constructor";
    String messageDrivers = "Store(): check if the array of drivers is initialized correctly";

    DeliveryDriver d0 = new DeliveryDriver("Driver0");
    DeliveryDriver d1 = new DeliveryDriver("Driver1");
    DeliveryDriver d2 = new DeliveryDriver("Driver2");
    DeliveryDriver[] expectedDrivers = {d0, d1, d2};

    Store store = new Store(expectedName, expectedDrivers);
    assertEquals(messageName, expectedName, store.getStoreName());
    assertArrayEquals(messageDrivers, expectedDrivers, store.getDrivers());
  }

  @Test(timeout = 100)
  // (0.0132)
  public void testPlaceOrderRevenueFirst() {
    double delta = 0.001;
    double expectedRevenue;
    String message = "Store::placeOrder()/getGrossRevenue(): check that revenue increases when an order is placed";
    Sandwich sand = new Sandwich("sand", 100, 300);
    Store store = new Store("store", 2);

    store.placeOrder(sand);
    expectedRevenue = 300;
    assertEquals(message, expectedRevenue, store.getGrossRevenue(), delta);

  }

  @Test(timeout = 100)
  // (0.0132)
  public void testPlaceOrderRevenueAccum() {
    double delta = 0.001;
    double expectedRevenue;
    String message = "Store::placeOrder()/getGrossRevenue(): check that revenue increases when consecutive orders are placed";
    Sandwich sand = new Sandwich("sand", 100, 500);
    SideOrder ord = new SideOrder("ord", 50, 550);
    Store store = new Store("store", 2);

    store.placeOrder(sand);
    expectedRevenue = 500;
    assertEquals(message, expectedRevenue, store.getGrossRevenue(), delta);

    store.placeOrder(ord);
    expectedRevenue = 1050;
    assertEquals(message, expectedRevenue, store.getGrossRevenue(), delta);
  }

  @Test(timeout = 100)
  // (0.0132)
  public void testPlaceOrderMatCost() {
    double delta = 0.001;
    double expectedMaterialCosts;
    String message = "Store::placeOrder()/getMaterialCost(): check that material costs increase when an order is placed";

    Sandwich sand = new Sandwich("sand", 50, 200);
    Store store = new Store("store", 2);

    store.placeOrder(sand);
    expectedMaterialCosts = 50;
    assertEquals(message, expectedMaterialCosts, store.getMaterialCosts(), delta);

  }

  @Test(timeout = 100)
  // (0.0132)
  public void testPlaceOrderMatCostAccum() {
    double delta = 0.001;
    double expectedMaterialCosts;
    String message = "Store::placeOrder()/getMaterialCost() check that material costs increase when consecutive orders are placed";
    Sandwich sand = new Sandwich("sand", 50, 200);
    SideOrder ord = new SideOrder("ord", 30, 150);
    Store store = new Store("store", 2);

    store.placeOrder(sand);
    expectedMaterialCosts = 50;
    assertEquals(message, expectedMaterialCosts, store.getMaterialCosts(), delta);

    store.placeOrder(ord);
    expectedMaterialCosts = 80;
    assertEquals(message, expectedMaterialCosts, store.getMaterialCosts(), delta);
  }

  @Test(timeout = 100)
  // (0.0132)
  public void testPlaceOrderAssignDriverFirst() {
    String message = "Store::placeOrder() check that orders are assigned to current driver";
    DeliveryDriver d0 = new DeliveryDriver("Driver0");
    DeliveryDriver d1 = new DeliveryDriver("Driver1");
    DeliveryDriver[] drivers = {d0, d1};
    Sandwich sand = new Sandwich("sand", 20, 30);
    SideOrder ord = new SideOrder("ord", 50, 70);
    sand.setDeliveryTime(15);
    ord.setDeliveryTime(15);
    Store store = new Store("store", drivers);

    store.placeOrder(sand);
    store.placeOrder(ord);
    PurchasedItem[] expectedOrders = {sand, ord};
    PurchasedItem[] expectedOrdersEmpty = {};
    assertArrayEquals(message, expectedOrders, drivers[0].getOrders());
    assertArrayEquals(message, expectedOrdersEmpty, drivers[1].getOrders());

  }

  @Test(timeout = 100)
  // (0.0132)
  public void testPlaceOrderAssignDriverSkipOrder1() {
    String message = "Store::placeOrder() check that orders are assigned to next driver when current driver is full";
    DeliveryDriver d0 = new DeliveryDriver("Driver0", 5, 0);
    DeliveryDriver d1 = new DeliveryDriver("Driver1", 5, 2);
    DeliveryDriver[] drivers = {d0, d1};
    Sandwich sand = new Sandwich("sand", 2, 10);
    SideOrder ord = new SideOrder("ord", 2, 10);
    sand.setDeliveryTime(30);
    ord.setDeliveryTime(15);
    Store store = new Store("store", drivers);

    store.placeOrder(sand);
    store.placeOrder(ord);
    PurchasedItem[] expectedOrders = {sand, ord};
    PurchasedItem[] expectedOrdersEmpty = {};
    assertArrayEquals(message, expectedOrders, drivers[1].getOrders());
    assertArrayEquals(message, expectedOrdersEmpty, drivers[0].getOrders());

  }

  @Test(timeout = 100)
  // (0.0132)
  public void testPlaceOrderAssignDriverSkipOrder2() {
    String message = "Store::placeOrder() check that orders are assigned to current driver until he is full";
    DeliveryDriver d0 = new DeliveryDriver("Driver0", 50, 1);
    DeliveryDriver d1 = new DeliveryDriver("Driver1", 50, 1);
    DeliveryDriver[] drivers = {d0, d1};
    Sandwich sand = new Sandwich("sand", 2, 10);
    SideOrder ord = new SideOrder("ord", 2, 10);
    sand.setDeliveryTime(14);
    ord.setDeliveryTime(13);
    Store store = new Store("store", drivers);

    store.placeOrder(sand);
    store.placeOrder(ord);

    PurchasedItem[] expectedOrders = {ord};
    assertArrayEquals(message, expectedOrders, drivers[1].getOrders());
  }

  @Test(timeout = 100)
  // (0.0132)
  public void testPlaceOrderAssignDriverSentOut() {
    String message = "Store::placeOrder() check the case when a driver must be sent out for delivery";
    DeliveryDriver d0 = new DeliveryDriver("Driver0", 5, 1);
    DeliveryDriver d1 = new DeliveryDriver("Driver1", 5, 1);
    DeliveryDriver[] drivers = {d0, d1};
    Sandwich sand = new Sandwich("sand", 2, 10);
    SideOrder ord = new SideOrder("ord", 2, 10);
    sand.setDeliveryTime(18);
    ord.setDeliveryTime(15);
    Store store = new Store("store", drivers);

    store.placeOrder(sand);
    store.placeOrder(ord);

    PurchasedItem[] expectedOrdersEmpty = {};
    assertArrayEquals(message, expectedOrdersEmpty, drivers[0].getOrders());

  }

  @Test(timeout = 100)
  // (0.0132)
  public void testPlaceOrderAssignDriverOnlyDriver() {
    String message = "Store::placeOrder() check the case of having only one driver";
    DeliveryDriver d0 = new DeliveryDriver("Driver0", 5, 1);
    DeliveryDriver[] drivers = {d0};
    Sandwich sand = new Sandwich("sand", 2, 10);
    SideOrder ord = new SideOrder("ord", 2, 10);
    sand.setDeliveryTime(16);
    ord.setDeliveryTime(17);
    Store store = new Store("store", drivers);

    store.placeOrder(sand);
    PurchasedItem[] expectedOrdersOne = {sand};
    assertArrayEquals(message, expectedOrdersOne, drivers[0].getOrders());

    store.placeOrder(ord);
    PurchasedItem[] expectedOrdersTwo = {ord};
    assertArrayEquals(message, expectedOrdersTwo, drivers[0].getOrders());
  }

  @Test(timeout = 100)
  // (0.0132)
  public void testPlaceOrderAssignDriverCircular() {
    String message = "Store::placeOrder() check the case when all drivers have already been sent for delivery";
    DeliveryDriver d0 = new DeliveryDriver("Driver0", 5, 1);
    DeliveryDriver d1 = new DeliveryDriver("Driver0", 5, 1);
    DeliveryDriver[] drivers = {d0, d1};
    Sandwich sand = new Sandwich("sand", 20, 200);
    SideOrder ord = new SideOrder("ord", 30, 200);
    Sandwich sand2 = new Sandwich("sand2", 31, 201);
    sand.setDeliveryTime(7);
    ord.setDeliveryTime(14);
    sand2.setDeliveryTime(17);
    Store store = new Store("store", drivers);

    store.placeOrder(sand);
    store.placeOrder(ord);
    store.placeOrder(sand2);

    PurchasedItem[] expectedOrders = {sand2};

    assertArrayEquals(message, expectedOrders, drivers[0].getOrders());
  }

  @Test(timeout = 100)
  // (0.0198)
  public void testCancelOrderRevenueFirst() {
    double delta = 0.001;
    double expectedRevenue = 0;
    String message = "Store::cancelOrder(): check revenue when order is canceled";

    Sandwich sand = new Sandwich("sand", 12, 20);
    Store store = new Store("store", 2);

    store.placeOrder(sand);
    store.cancelOrder(sand);

    assertEquals(message, expectedRevenue, store.getGrossRevenue(), delta);

  }

  @Test(timeout = 100)
  // (0.0198)
  public void testCancelOrderRevenueAccum() {
    double delta = 0.001;
    String message = "Store::cancelOrder(): check revenue when consecutive orders are canceled";
    Sandwich sand = new Sandwich("sand", 200, 200);
    SideOrder ord = new SideOrder("ord", 52, 52);
    Store store = new Store("store", 2);

    store.placeOrder(sand);
    store.placeOrder(ord);
    store.cancelOrder(sand);
    assertEquals(message, 52, store.getGrossRevenue(), delta);
    store.cancelOrder(ord);
    assertEquals(message, 0, store.getGrossRevenue(), delta);
  }

  @Test(timeout = 100)
  // (0.0198)
  public void testCancelOrderMaterialCost() {
    double delta = 0.001;
    double expectedMaterialCosts = 15;
    String message = "Store::cancelOrder() check material cost when order is canceled";
    Sandwich sand = new Sandwich("sand", 10, 20);
    SideOrder ord = new SideOrder("ord", 5, 15);
    Store store = new Store("store", 2);

    store.placeOrder(sand);
    store.cancelOrder(sand);
    store.placeOrder(ord);
    store.cancelOrder(sand);

    assertEquals(message, expectedMaterialCosts, store.getMaterialCosts(), delta);
  }

  @Test(timeout = 100)
  // (0.0198)
  public void testCancelOrderFalseNotCurrent() {
    String message = "Store::cancelOrder() check when current driver doesn't hold the item";
    DeliveryDriver d0 = new DeliveryDriver("Driver0", 5, 1);
    DeliveryDriver d1 = new DeliveryDriver("Driver0", 5, 1);
    DeliveryDriver[] drivers = {d0, d1};
    Sandwich sand = new Sandwich("sand", 100, 200);
    SideOrder ord  = new SideOrder("ord", 100, 200);
    sand.setDeliveryTime(15);
    ord.setDeliveryTime(15);
    Store store = new Store("store",drivers);
    store.placeOrder(sand);
    store.placeOrder(ord);

    assertFalse(message, store.cancelOrder(sand));

  }

  @Test(timeout = 100)
  // (0.0198)
  public void testCancelOrderTrueCurrent() {
    String message = "Store::cancelOrder() check when current driver holds the item";
    DeliveryDriver d0 = new DeliveryDriver("Driver0", 5, 1);
    DeliveryDriver d1 = new DeliveryDriver("Driver0", 5, 1);
    DeliveryDriver[] drivers = {d0, d1};
    SideOrder ord  = new SideOrder("ord", 100, 200);
    ord.setDeliveryTime(15);
    Store store = new Store("store",drivers);
    store.placeOrder(ord);

    assertTrue(message, store.cancelOrder(ord));

  }

  @Test(timeout = 100)
  // (0.033)
  public void testNetProfit() {
    double delta = 0.001;
    String message = "Store::getNetProfit() check that net profit is calculated correctly";
    double expectedNetProfit = 541.667;

    DeliveryDriver d1= new DeliveryDriver("name", 70, 5);
    DeliveryDriver d2= new DeliveryDriver("name", 80, 1);
    DeliveryDriver[] drivers = {d1,d2};
    Store store = new Store("store", drivers);

    for (int i = 0; i < 6; i++) {
      Sandwich sand = new Sandwich("1", 100, 200);
      sand.setDeliveryTime(10);
      store.placeOrder(sand);
    }

    assertEquals(message, expectedNetProfit, store.getNetProfit(), delta);

  }


  @Test(timeout = 100)
  // (0.033)
  public void testNetIncome() {
    double delta = 0.001;
    String message = "Store::getNetProfit() check that net income is calculated correctly";
    double expectedNetIncome = 419.625;

    DeliveryDriver d1= new DeliveryDriver("name", 30, 5);
    DeliveryDriver d2= new DeliveryDriver("name", 30, 1);
    DeliveryDriver[] drivers = {d1,d2};

    Store store = new Store("store", drivers);
    for (int i = 0; i < 6; i++) {
      Sandwich sand = new Sandwich("1", 100, 200);
      sand.setDeliveryTime(19);
      store.placeOrder(sand);
    }

    assertEquals(message, expectedNetIncome, store.getNetIncome(), delta);
  }

}
