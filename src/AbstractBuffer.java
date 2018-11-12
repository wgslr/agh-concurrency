public abstract class AbstractBuffer {
    long size;
    long contentCount = 0;

    public AbstractBuffer(long size) {
        this.size = size;
    }

    public final void put(long portions) {
        long startNs = System.nanoTime();
        do_put(portions);
        long duration = System.nanoTime() - startNs;

        System.out.println(String.format("%s; put; %d; %d", name(), portions, duration));
    }

    public final void get(long portions) {
        long startNs = System.nanoTime();
        do_get(portions);
        long duration = System.nanoTime() - startNs;

        System.out.println(String.format("%s; get; %d; %d", name(), portions, duration));

    }

    public abstract void do_put(long portions);

    public abstract void do_get(long portions);

    public abstract String name();

    protected long freeSpace() {
        return size - contentCount;
    }
}
