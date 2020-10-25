class bool {
	public static void main(String[] args) {
		boolean a = true;
		boolean b = false;
		int c = 1;
		int d = 2;

		System.out.println(a && b);								// false
		System.out.println(a || b);								// true

		System.out.println(a || (c == d));						// true
		System.out.println(a || (b && (c <= d) && (c != d)));	// true
	}
}