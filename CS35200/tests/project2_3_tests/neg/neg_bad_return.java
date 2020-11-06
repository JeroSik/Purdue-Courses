class Main {
    public static void main(String[] args) {
        A a = new A();
        int b = a.a();  // No error
    }
}

class A {
    int a() {
        return "hello"; // Error
    }
}