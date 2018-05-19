import java.util.concurrent.atomic.AtomicInteger;

public class Counter2 implements Counter {
    private AtomicInteger c = new AtomicInteger(0);

    public void inc() {
        c.incrementAndGet();
    }

    public void dec() {
        c.decrementAndGet();
    }

    public int get() {
        return c.get();
    }
}
