class Main {
    public static void main(String[] args) {
        A a = new A();
        int ans = a.plus();
        System.out.println(ans);
    }
}

class A {
    int x = 1;
    int y = x;

    int plus() {
        return this.x + this.y;
    }
}