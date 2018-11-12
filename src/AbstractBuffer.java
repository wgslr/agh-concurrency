public abstract class AbstractBuffer {
    long size;
    long contentCount = 0;

    public AbstractBuffer(long size) {
        this.size = size;
    }

    public final void put(Long portions) {
        long startNs = System.nanoTime();
        System.out.println(String.format("start;  %s; put; %d", name(), portions));

        do_put(portions);

        long duration = System.nanoTime() - startNs;
        System.out.println(String.format("finish; %s; put; %d; %d; %d", name(), portions, duration,
                contentCount));
    }

    public final void get(Long portions) {
        long startNs = System.nanoTime();
        System.out.println(String.format("start;  %s; get; %d", name(), portions));

        do_get(portions);

        long duration = System.nanoTime() - startNs;
        System.out.println(String.format("finish; %s; get; %d; %d; %d", name(), portions,
                duration, contentCount));

    }

    public abstract void do_put(long portions);

    public abstract void do_get(long portions);

    public abstract String name();

    protected long freeSpace() {
        return size - contentCount;
    }
}
