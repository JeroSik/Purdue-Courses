import java.util.concurrent.atomic.AtomicInteger;

public class Divider implements Runnable {
    private static AtomicInteger counter = new AtomicInteger(0);
    private int start;
    private int end;
    private static Object lock = new Object();

    public Divider(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        for (int i = start; i <= end; i++) {
            if ((i % 7) == 0) {
                counter.incrementAndGet();
            }
        }
    }

    public static void main(String[] args){
        Thread t1 = new Thread(new Divider(1, 1000));
        Thread t2 = new Thread(new Divider(1001, 2000));
        Thread t3 = new Thread(new Divider(2001, 3000));
        t1.start();
        t2.start();
        t3.start();
        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            System.out.println("Big problem");
            System.exit(1);
        }
        System.out.println(counter.get());
    }
}
