class Foo {
    public static void main(String[] args) {
        // // Bar bar = new Bar();
        // int a = new Bar().some_method();
        // System.out.println(a);

        // Bar bar = new Bar();
        // int a = bar.fizz.buzz.please(5);
        // // Fizz f = new Fizz();
        // // int a = f.buzz.please(5);
        // System.out.println(a);

        Bar bar = new Bar();
        System.out.println(bar.hehe);
        bar.change(10);
        System.out.println(bar.hehe);
    }
}

class Bar {
    int some_field = 10;
    int hehe = 5;
    
    int change(int a) {
        this.hehe = a;
        return 7;
    }

    // public int some_method() {
    //     return 5;
    // }
    // Fizz fizz = new Fizz();
}

// class Fizz {
//     Buzz buzz = new Buzz();
// }

// class Buzz {
//     public int please(int a) {
//         return a;
//     }
// }