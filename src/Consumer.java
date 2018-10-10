public class Consumer implements Runnable {
    private Buffer buffer;
    private long id;

    public Consumer(Buffer buffer, long id) {
        this.buffer = buffer;
        this.id = id;
    }

    public void run() {
        for (int i = 0; i < Lab1App.MSG_PER_CONS; i++) {
            String message = null;
            message = buffer.take();
            System.out.println("Consumer " + id + " received: " + message);
        }

    }
}

