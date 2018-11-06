import java.util.ArrayList;
import java.util.List;

public class WaiterApp {
    public final static int LOOPS = 2;
    public final static int CLIENTS = 4;
    public final static int PAIRS = CLIENTS / 2;

    public static void main(String argv[]) throws InterruptedException {
        Waiter w = new Waiter();

        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < CLIENTS; ++i) {
            threads.add(new Thread(new Client(w, i, i % PAIRS, LOOPS)));
        }

        threads.forEach(Thread::start);

        for (Thread thread : threads) {
            thread.join();
        }
    }

}
