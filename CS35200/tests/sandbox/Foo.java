class Foo {
    public static void main(String[] args) {
        Bar bar = new Bar();
        Fizz fizz = new Fizz();
        System.out.println(bar.some_int);
        fizz.fizz_method(bar);
        System.out.println(bar.some_int);
    }
}

class Bar {
    int some_int = 2;
}

class Fizz {
    public boolean fizz_method(Bar b) {
        b.some_int = 5;
        return true;
    }
}