class wrongarray {
	public static void main(String[] args) {
		int[] a = false;	// type error
		int b = a[0] + 1; 	// no error
		int c = a + 1; 		// type error

		int[][] d = false;	// type error
		int e = d[0] + 1; 	// type error
		int f = d + 1; 		// type error

		int g = false;		// type error
		int h = g[0] + 1;	// type error
		int i = g + 1;		// no error


		// dimension mismatch, type error
		int[] j = new int[1][2];
		int[][] k = new int[1];

		// correct init
		int[] l = new int[1];
		int[] m = new int[1];


		int n;
		n = l + m;			// type error
		n = l - m;			// type error
		n = l * m;			// type error
		n = l / m;			// type error

		int[] o;
		n = n + o[0];		// type error because of n

		n = o[0];
		n = l.length + 1;
		n = l[true];		// type error
	}
}
/*
3
5

7
8
9

11
12

17
18

26
27
28
29

32
36
 */
