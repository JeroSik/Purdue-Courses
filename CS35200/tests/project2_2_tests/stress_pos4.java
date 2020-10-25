class stresssort {
	public static void main(String[] args) {
		int l = 1000000;
		int[] a = new int[l];
		int i = 0;
		while (i < l) {
			a[i] = i;
			System.out.println(a[i]);
			i = i + 1;
		}

		System.out.println(a[12351]);

		i = 0;
		int j = 0;

		while (i < l) {
			while (j < l - i - 1) {
				if (a[j] > a[j+1]) {
					int k = a[j];
					a[j] = a[j+1];
					a[j+1] = k;
				}
				else {

				}

				j = j + 1;
			}
			i = i + 1;
		}

		i = 0;
		while (i < l - 1) {
			if (a[i] > a[i+1]) {
				System.out.println("Wrong\n");
			}
			else {

			}

			i = i + 1;
		}
	}
}
