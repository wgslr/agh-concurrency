public class Counter {
    private long value = 0;

    public long getValue() {
        return value;
    }

    public void increase() {
        value += 1;
    }

    public void decrease() {
        value -= 1;
    }

}
