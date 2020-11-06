class Foo {
    public static void main(String[] args) {
        Bar a = new Bar();
        a.some_method(10, "Goodbye");
    }
}

class Bar {
    public int some_method(int a, String b) {
        b = "Hello";
        a = true;                               // Error
        b = b + b;
        System.out.println(b);
        System.out.println("HelloHello");

        return 12;
    }
}