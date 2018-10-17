public class Semaphore {
    int count;

    public Semaphore(int initialCount) {
        this.count = initialCount;
    }

    public synchronized int getCount() {
        return count;
    }

    public synchronized void P() {
        while (count == 0) {
            try {
                notify();
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        --count;
    }

    public synchronized void V() {
        ++count;
        notify();
    }

}

