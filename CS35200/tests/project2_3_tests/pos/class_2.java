class Main {
    public static void main(String[] args) {
        a a = new a();

        int b = a.a + a.b();
        int d = new b().c();
    }
}

class a {
    int a = 1;
    public int b() {
        return 1;
    }
}

class b {
    int c() {
        return 5;
    }
}