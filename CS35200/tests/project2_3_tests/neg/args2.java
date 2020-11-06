class Foo {
    public static void main(String[] args) {
        Bar bar = new Bar();
        String a = "10";
        String b = "120";
        String c = "Hello";
        String d = "true";

        bar.some_method(a, b, c, d);
        bar.some_method(a, b, c, d, "extra arg");
    }
}

class Bar {
    public boolean some_method(String a, String b,  String c,  String d) {
        System.out.println(a + b + c + d);
        return true;
    }
}