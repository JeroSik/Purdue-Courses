class Foo {
    public static void main(String[] args) {
        A a = new A();
        a.b.c = "goodbye";
        System.out.println(a.b.c);
    }
}

class A {
    B b = new B();
}

class B {
    String c = "hello";
}