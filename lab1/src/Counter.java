public class Counter {
    private long value = 0;

    public long getValue() {
        return value;
    }

    public synchronized void increase() {
        value += 1;
    }

    public synchronized void decrease() {
        value -= 1;
    }

}