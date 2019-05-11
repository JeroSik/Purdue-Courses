import java.util.Scanner;

public class ComputerCustomizer {
  public static void main (String[] args) {
    Scanner scan = new Scanner(System.in);
    double processorCost;
    double storageCost = 0;
    double batteryCost = 0;
    double memoryCost = 0;

    System.out.println("CS18000's Computer Customizer\n" + "\nStarting price: $1799.00");

    System.out.println("Processor choices\n" + "(1) Intel Core i3 (+ $0)\n" + "(2) Intel Core i5 (+ $150)\n" + "(3) Intel Core i7 (+ $400)\n");
    System.out.print("Your choice: ");
    int processor = scan.nextInt();
    System.out.println("\nMemory choices\n" + "(1) 8GB DDR4 RAM (+ $0)\n" + "(2) 16GB DDR4 RAM (+ $200)\n" + "(3) 32GB DDR4 RAM (+ $600)\n");
    System.out.print("Your choice: ");
    int memory = scan.nextInt();
    System.out.println("\nStorage choices\n" + "(1) 256GB SSD (+ $0)\n" + "(2) 512GB SSD (+ $300)\n" + "(3) 1TB SSD (+ $700)\n");
    System.out.print("Your choice: ");
    int storage = scan.nextInt();
    System.out.println("\nBattery choices\n" + "(1) 10 HR Battery (+ $0)\n" + "(2) 15 HR Battery (+ $100)\n" + "(3) 20 HR Battery (+ $200)\n");
    System.out.print("Your choice: ");
    int battery = scan.nextInt();

    switch(processor) {
      case 1: processorCost = 0.0;
        break;
      case 2: processorCost = 150.0;
        break;
      default: processorCost = 400.0;
        break;
    }
    switch(memory) {
      case 1:
        memoryCost = 0.0;
        break;
      case 2:
        memoryCost = 200.0;
        break;
      default:
        memoryCost = 600.0;
        break;
    }
    switch(storage) {
      case 1:
        storageCost = 0.0;
        break;
      case 2:
        storageCost = 300.0;
        break;
      default:
        storageCost = 700.0;
        break;
    }
    switch(battery) {
      case 1: batteryCost = 0.0;
        break;
      case 2: batteryCost = 100.0;
        break;
      default: batteryCost = 200.0;
        break;
    }

    double totalCost = 1799.00 + processorCost + memoryCost + storageCost + batteryCost;
    System.out.printf("\nTotal cost: $%.2f",totalCost);
  }
}
