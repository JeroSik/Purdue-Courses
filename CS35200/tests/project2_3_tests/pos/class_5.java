class Foo {
    public static void main(String[] args) {
        A a = new A();
        System.out.println(a.b.c.d.hello(500));
    }
}

class A {
    B b = new B();
    int x = 1;
}

class B {
    C c = new C();
    int x = 10;
}

class C {
    D d = new D();
    int x = 100;
}

class D {
    int x = 1000;

    int hello(int x) {
        System.out.println("----------");
        System.out.println(x);              //500
        System.out.println(this.x);         //1000
        this.x = x;
        System.out.println("----------");
        System.out.println(x);              //500
        System.out.println(this.x);         //500
        System.out.println("----------");
        return this.x;                      //500
    }
}