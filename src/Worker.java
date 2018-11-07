public class Worker implements Runnable {
    Buffer buffer;
    String predecessor;
    String id;

    public Worker(Buffer buffer, String id, String predecessor) {
        this.buffer = buffer;
        this.id = id;
        this.predecessor = predecessor;
    }

    @Override
    public void run() {
        for (int i = 0; i < buffer.getSize(); ++i) {
            System.out.println(String.format("Worker %s waiting for %d to become %s", id, i,
                    predecessor));

            buffer.waitFor(i, predecessor);
            System.out.println(String.format("Worker %s working on %d", id, i));

            Delayer.randomDelay(100, 3000);

            buffer.set(i, id);
            System.out.println(String.format("Worker %s set %d to %s\n%s", id, i, id, buffer));

        }
    }
}
