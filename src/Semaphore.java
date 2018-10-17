public class Semaphore {
    boolean state;

    public Semaphore(boolean isUp) {
        this.state = isUp;
    }

    public synchronized void P() {
        while (!this.state) {
            notify();

            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.state = false;
    }

    public synchronized void V() {
        this.state = true;
        notify();
    }
}
