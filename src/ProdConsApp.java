import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class ProdConsApp {

    public final static long WORKERS = 10;
    public final static long M = 40;
    public final static long LOOPS = 1; // number of operations each thread performs

    public final static long LOCK_TIMEOUT_SECONDS = 10;

    public static void main(String argv[]) throws InterruptedException {
        long workers = WORKERS;
        long m = M;
        if(argv.length >= 1) {
            workers = Integer.parseInt(argv[0]);
        }
        if(argv.length >= 2) {
            m = Integer.parseInt(argv[1]);
        }

        AbstractBuffer b = new NaiveBuffer(m * 2);

        List<Thread> threads = new ArrayList<>();

        for(int i = 0; i < workers; ++i){
            Worker w = new Worker(b::put, m, LOOPS);
            threads.add(new Thread(w));
        }
        for(int i = 0; i < workers; ++i){
            Worker w = new Worker(b::get, m, LOOPS);
            threads.add(new Thread(w));
        }

        threads.forEach(Thread::start);

        for (Thread thread : threads) {
            thread.join();
        }

    }
}
