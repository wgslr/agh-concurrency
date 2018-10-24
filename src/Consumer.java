public class Consumer implements Runnable {
    private BoundedBuffer buffer;
    private long id;
    private long count;

    public Consumer(BoundedBuffer buffer, long id) {
        this.buffer = buffer;
        this.id = id;
        this.count = 0;
    }

    public void run() {
        for (int i = 0; i < Lab1App.MSG_PER_CONS; i++) {
            String message = null;
            try {
                message = (String) buffer.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ++count;
            System.out.println("Consumer " + id + " received " + count + "th " + message);
        }

    }
}

