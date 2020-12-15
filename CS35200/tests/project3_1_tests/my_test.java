// class all1{
//     public static void main(String[] args){
//         System.out.print(args[0]);

//         int a = new haha().someMethod(5);
//         System.out.println(a);
//     }
// }

// class haha {
//     public int someMethod(int a){
//         System.out.println(a);

//         if (a <= 1)
//             return a;
//         else
//             System.out.println("hello");
        
//         return someMethod(a-1);
//     }
// }

class stress{
	public static void main(String[] args){
		System.out.println(args[0]);
		System.out.println(Integer.parseInt(args[1]));

		int b = Integer.parseInt(args[2]);
		int a = new random().haha(b+1);

		if(a <= b || a == b){
			System.out.print("True");
			while(a < 12){
				System.out.println("hello");
				a = a + 2;
			}
		}
		else{
			while(a < 13){
				System.out.print("cs352");
			}
		}
	}

}

class random{
	public int haha(int a){
		if(a > 10){
			return 100; 

		}else{
			System.out.println("Hello");
		}

        return haha(a+1);
	}

}