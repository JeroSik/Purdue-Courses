class wrongscope {
	public static void main(String[] args) {
		String a = "smart";

		if (true) {
			String b = "Daniel is ";
			a = "stupid";
		}
		else {

		}

		System.out.print(b);
		System.out.println(a);
	}
}
