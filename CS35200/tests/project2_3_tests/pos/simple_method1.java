class Foo {
    public static void main(String[] args) {
        Bar bar = new Bar();
        int a = bar.some_method();

        System.out.println(a);
    }
}

class Bar {
    public int some_method() {
        return 5;
    }
}