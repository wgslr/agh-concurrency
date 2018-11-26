public class Consumer implements Runnable {
    private Buffer buffer;
    private long id;
    private long count;

    public Consumer(Buffer buffer, long id) {
        this.buffer = buffer;
        this.id = id;
        this.count = 0;
    }

    public void run() {
        for (int i = 0; i < Lab1App.MSG_PER_CONS; i++) {
            String message = null;
            message = buffer.take();
            ++count;
            System.out.println("Consumer " + id + " received " + count + "th " + message);
        }

    }
}

