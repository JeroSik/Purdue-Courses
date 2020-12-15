class Test{
    public static void main(String[] args) {
		int a = 2;
        int b = 3;
        while(a < 10){
            System.out.print("a: ");
            System.out.println(a);
            while(b <= 7){
                System.out.print("b: ");
                System.out.println(b);
                a = a - 1;
                b = b + 1;
            }
            a = a + 1;
        }
        System.out.println("One final print");
    }
}