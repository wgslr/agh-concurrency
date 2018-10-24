public class Producer implements Runnable {
    private BoundedBuffer buffer;

    public Producer(BoundedBuffer buffer) {
        this.buffer = buffer;
    }

    public void run() {
        for (int i = 1; i <= Lab1App.MESSAGES; i++) {
            String msg = "message: " + i;
            System.out.println("Put " + msg);
            try {
                buffer.put(msg);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
