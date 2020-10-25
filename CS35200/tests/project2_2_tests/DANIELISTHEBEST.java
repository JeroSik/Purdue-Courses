class stress2{
	public static void main(String[] args){
		String[][] test = new String[10][13];
		String[] replace = new String[13];

		int index = 0;
		while(index < replace.length){
			replace[index] = "HELLO";
			index = index + 1;
		}

		int i = 0;
		while(i < test.length){
			int j = 0;
			while(j < test[4].length){
				test[i][j] = "LOL";
				j = j+1;
			}
			i = i+1;
		}

        i = 0;
		while(i < test.length){
			int j = 0;
            System.out.print(j);
			while(j < test[4].length){
				System.out.print(test[i][j]);
                System.out.print(" ");
				j = j+1;
			}
            System.out.println("");
			i = i+1;
		}


		test[3] = replace;
		
		while(i > 0){
			int j = test[0].length;
			while(j > 0){
				System.out.println(test[i-1][j-1]);
				j = j - 1;
			}
			i = i + -1;
		}

	}
}