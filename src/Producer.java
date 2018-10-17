public class Producer implements Runnable {
    private Buffer buffer;

    public Producer(Buffer buffer) {
        this.buffer = buffer;
    }

    public void run() {
        for (int i = 1; i <= Lab1App.MESSAGES; i++) {
            String msg = "message: " + i;
            System.out.println("Put " + msg);
            buffer.put(msg);
        }
    }
}
