class Foo {
    public static void main(String[] args) {
        Bar bar = new Bar();
        bar.dup_method(10, "Hello");
    }
}

class Bar {
    public boolean dup_method(int a, String b) {
        return true;
    }
    public boolean dup_method(int a, String b) {
        return true;
    }
}