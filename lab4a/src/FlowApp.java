import java.util.ArrayList;
import java.util.List;

public class FlowApp {

    public final static int THREADS = 10;
    public final static int CELLS = 100;

    public static void main(String argv[]) throws InterruptedException {
        Buffer b = new Buffer(CELLS);
        System.out.println(b);

        List<Thread> threads = new ArrayList<>();

        for (int i = 'A'; i < 'A' + THREADS; ++i) {
            String pred = Character.toString((char) (i - 1));
            Worker w = new Worker(b, Character.toString((char) i), pred);
            threads.add(new Thread(w));
        }

        threads.forEach(t -> {
            Delayer.randomDelay(500, 2000);
            t.start();
        });

        for (Thread thread : threads) {
            thread.join();
        }
    }

}
