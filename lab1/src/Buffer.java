public class Buffer {
    String value;

    public Buffer() {
        value = null;
    }

    public synchronized void put(String str) {
        while (value != null) {
            try {
                notify();
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        value = str;
        notify();
    }

    public synchronized String take() {
        while (value == null) {
            try {

                notify();
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
