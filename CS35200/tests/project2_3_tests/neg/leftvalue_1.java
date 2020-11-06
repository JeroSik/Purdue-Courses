class Main {
    public static void main(String[] args) {
    }
}

class A {
    int x = 1;
    int y = x;

    int[] plus() {
        int[] arr = new int[5];
        return arr;
    }
}

class B {
    String x = "hello";

    int some_method() {
        int x = 5;
        x = "goodbye";
        this.x = "lmao";
        x = 3;
        return true;
    }
}