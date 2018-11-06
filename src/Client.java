import com.sun.org.apache.xml.internal.serialize.Printer;

public class Client implements Runnable {

    private Waiter waiter;
    private int id;
    private int pair;
    private int loops;

    public Client(Waiter waiter, int id, int pair, int loops) {
        this.waiter = waiter;
        this.id = id;
        this.pair = pair;
        this.loops = loops;
    }

    public void run() {
        while (loops-- > 0) {
            Delayer.randomDelay(100, 1000);

            log("Requesting table");
            boolean sat = waiter.requestTable(pair);
            assert sat;
            log("Sat at the table");

            Delayer.randomDelay(500, 3000);

            log("Leaving the table");
            waiter.leave(pair);
        }

    }

    private void log(String text) {
        System.out.println("Client " + id + " (pair " + pair + "): " + text);
    }
}
