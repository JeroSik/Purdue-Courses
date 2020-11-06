class Foo {
    public static void main(String[] args) {
        A a = new A();
        System.out.println(a.m1());
    }
}

class A {
    int i = 0;

    public int m1() {
        System.out.println("m1");
        if (i < 5) {
            i = i + 1;
        } else {
            return -10;
        }
    }

    public int m2() {
        System.out.println("m2");
        System.out.println(i);
        m1();
    }
}