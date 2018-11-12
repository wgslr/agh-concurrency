import java.util.ArrayList;
import java.util.List;

public class ProdConsApp {

    public final static int PRODUCERS = 10;
    public final static int CONSUMERS = PRODUCERS;
    public final static int M = 40;
    public final static int SIZE = 80;

    public static void main(String argv[]) throws InterruptedException {
        AbstractBuffer b = new NaiveBuffer()
        System.out.println(b);

//        List<Thread> threads = new ArrayList<>();
//
//        for (int i = 'A'; i < 'A' + THREADS; ++i) {
//            String pred = Character.toString((char) (i - 1));
//            Worker w = new Worker(b, Character.toString((char) i), pred);
//            threads.add(new Thread(w));
//        }
//
//        threads.forEach(t -> {
//            Delayer.randomDelay(500, 2000);
//            t.start();
//        });
//
//        for (Thread thread : threads) {
//            thread.join();
//        }
    }
}
