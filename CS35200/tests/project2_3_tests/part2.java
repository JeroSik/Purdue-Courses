class stress2{
	public static void main(String[] args){
        int n = ((10 * (5 + 5)) / 20) * -1;
        System.out.print("n: ");
        System.out.println(n);

		String[][] duo = new String[5][7];
		String[] solo = new String[7];
        int[] nums = new int[-n + 5];
        System.out.print("Length of duo: ");
        System.out.println(duo.length);
        System.out.print("Length of duo[]: ");
        System.out.println(duo[0].length);
        System.out.print("Length of solo: ");
        System.out.println(solo.length);
        System.out.print("Length of nums: ");
        System.out.println(nums.length);


		int index = 0;
		while(index < solo.length){
			solo[index] = "HELLO";
			index = index + 1;
		}
		int i = 0;
		while(i < duo.length){
			int j = 0;
			while(j < duo[i].length){
				duo[i][j] = "LOL";
				j = j+1;
			}
			i = i+1;
		}
		duo[2] = solo;
        i = 0;
		while(i < duo.length){
			int j = 0;
			while(j < duo[i].length){
				System.out.print(duo[i][j]);
                System.out.print(" ");
				j = j+1;
			}
            System.out.println("");
			i = i+1;
		}
		
        nums[4] = 1 + 3 + 5 + 6 + 7 / 3 + 10 * 3 - 1 + +1 - -2;
        System.out.print("nums[4]: ");
        System.out.println(nums[4]);

        System.out.println("--- booleans ---");
        System.out.println(!false);
        System.out.println(2 != 2);
        System.out.println(3 == 3);
        System.out.println(3 > -10);
        System.out.println(false || true);
	}
}