class Foo{
    public static void main(String[] args){
        int a = -1, b = 69, c = 420, d;
        d = 1337;
        int e = 8008;
        System.out.println(a + ((b + c - (d * (e * a))) * a));
        System.out.println((a + b) * d + 4 + e / b);
        System.out.println(a + ((b + c - (d * (e * a))) * a) > (a + b) * d + 4 + e / b);
    }
}