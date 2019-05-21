import java.util.Scanner;

/*
*       NOTE: This class won't compile until:
*             PurchasedItem, Spicyness, OrderSize, Sandwich and SideOrder have been implemented
*
*       Uncomment the code once you have finished your implementation
* */

public class Run {



    public static void run() {

        Scanner in = new Scanner(System.in);

        // Initialize the Store object
        Store st = null;
        String storeName;
        int nDrivers;

        // Initialize the Item object
        PurchasedItem pi = null;
        String customerName;
        double materialCost;
        double sellPrice;
        int condiments;
        int deliveryTime = 0;
        String sp;
        String sz;
        Spicyness level;
        OrderSize size;

        System.out.printf("What is the name of the store? ");
        storeName = in.nextLine();
        do {
            System.out.printf("How many drivers work at the store? ");
            nDrivers = in.nextInt();
            in.nextLine();
        } while (nDrivers < 1);
        //System.out.printf("Name: %s\nNum Drivers: %d\n", storeName, nDrivers);
        System.out.println();

        // Choose a store constructor and initialize drivers and store
        int action;
        do {
            System.out.printf("Your Options\n1.) Enter driver "
                    + "information manually\n2.) Don't enter driver "
                    + "information\nSelect an action: ");
            action = in.nextInt();
            in.nextLine();

            switch(action) {
                // initialize the driver objects
                case 1:
                    DeliveryDriver[] dr = new DeliveryDriver[nDrivers];
                    for (int i = 0; i < nDrivers; ++i) {
                        String driverName;
                        double driverWage;
                        int carriableItems;

                        System.out.printf("Input driver name: ");
                        driverName = in.nextLine();

                        System.out.printf("Input driver wage in $/hr "
                                + "(negative value to skip): ");
                        driverWage = in.nextDouble();
                        in.nextLine();

                        if (driverWage < 0) {
                            dr[i] = new DeliveryDriver(driverName);
                            continue;
                        }

                        System.out.printf("Input number of carriable "
                                + "items (negative or 0 to skip: ");
                        carriableItems = in.nextInt();
                        in.nextLine();

                        if (carriableItems < 1)
                            dr[i] = new DeliveryDriver(driverName, driverWage);
                        else
                            dr[i] = new DeliveryDriver(driverName, driverWage, carriableItems);
                        System.out.println();
                    }
                    st = new Store(storeName, dr);
                    break;

                case 2:
                    st = new Store(storeName, nDrivers);
                    break;

                default:
                    System.out.printf("Invalid selection\n\n");
            }
        } while(action != 1 && action != 2);

        System.out.printf("%s\n", st.toString());

        // Main body execution.
        boolean running = true;
        while (running) {
            System.out.printf("Your Options\n1.) Place Order "
                    + "\n2.) Cancel Order "
                    + "\n3.) View Store Information "
                    + "\n4.) Exit System "
                    + "\nSelect an action: ");
            action = in.nextInt();
            in.nextLine();
            System.out.println();

            if (action == 1 || action == 2) {

                System.out.printf("Enter customer name: ");
                customerName = in.nextLine();

                int type;
                do {
                    System.out.println();
                    System.out.printf("Choose: \n1) Sandwich\n2) Side Order ");
                    type = in.nextInt();
                    if (type != 1 && type != 2) {
                        System.out.printf("Invalid selection\n\n");
                        continue;
                    }
                    System.out.println();
                    System.out.printf("Enter material cost: ");
                    materialCost = in.nextDouble();
                    System.out.printf("Enter sale price: ");
                    sellPrice = in.nextDouble();
                    System.out.printf("Enter number of condiments: ");
                    condiments = in.nextInt();
                    in.nextLine();

                    if (type == 1) {
                        System.out.printf("Enter spicyness level: (MILD/MEDIUM/HOT/CRAZY/INSANE): ");
                        sp = in.nextLine();

                        switch (sp) {
                            case "MILD":
                                level = Spicyness.MILD;
                                break;
                            case "MEDIUM":
                                level = Spicyness.MEDIUM;
                                break;
                            case "HOT":
                                level = Spicyness.HOT;
                                break;
                            case "CRAZY":
                                level = Spicyness.CRAZY;
                                break;
                            case "INSANE":
                                level = Spicyness.INSANE;
                                break;
                            default:
                                level = Spicyness.MILD;
                                break;
                        }
                        pi = new Sandwich(customerName, materialCost, sellPrice, deliveryTime, level, condiments);

                    } else {
                        System.out.printf("Enter order size: (SMALL/MEDIUM/LARGE/ABSURD): ");
                        sz = in.nextLine();

                        switch (sz) {
                            case "SMALL":
                                size = OrderSize.SMALL;
                                break;
                            case "MEDIUM":
                                size = OrderSize.MEDIUM;
                                break;
                            case "LARGE":
                                size = OrderSize.LARGE;
                                break;
                            case "ABSURD":
                                size = OrderSize.ABSURD;
                                break;
                            default:
                                size = OrderSize.SMALL;
                                break;
                        }
                        pi = new SideOrder(customerName, materialCost, sellPrice, deliveryTime, size);
                    }
                } while (type != 1 && type != 2);

                System.out.printf("Enter delivery time: ");
                deliveryTime = in.nextInt();
                in.nextLine();
                pi.setDeliveryTime(deliveryTime);
                System.out.println();


                if (action == 1) {
                    st.placeOrder(pi);
                } else {
                    st.cancelOrder(pi);
                }
            } else if (action == 3) {
                System.out.printf("%s\n", st.toString());
            } else if (action == 4) {
                running = false;
            } else {
                System.out.printf("Invalid selection\n\n");
            }

        }

        in.close();
    }

    public static void main(String[] args) {
        Run.run();
    }



}
