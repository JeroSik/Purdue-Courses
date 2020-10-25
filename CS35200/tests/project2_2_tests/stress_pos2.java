class simpleloop {
    public static void main(String [] args) {
	int n = 10;
	if (n < 1) System.out.println("Enter a positive integer;\n");
	else {
	      System.out.print("Factorial of ");
	      System.out.print(n);
	      System.out.print(" is ");
	}
        int multiplier = 1;
        int prod = 1;
        while (multiplier <= 10) {
            prod = prod * multiplier;
            multiplier = multiplier + 1;
        }
        System.out.println(prod);
    }
}
