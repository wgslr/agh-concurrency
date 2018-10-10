public class Buffer {
    String value;

    public Buffer() {
        value = null;
    }

    public synchronized void put(String str) {
        while (value != null) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        value = str;
        notifyAll();
    }

    public synchronized String take() {
        while (value == null) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        String v = value;
        value = null;
        notify();
        return v;
    }
}
