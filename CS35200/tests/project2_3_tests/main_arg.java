class Foo{
    public static void main(String[] args) {
        System.out.println(args.length);

        int i = 0;
        while (i < args.length) {
            System.out.println(args[i]);
            i = i + 1;
        }
    }
}