import java.util.ArrayList;
import java.util.List;

public class PrintersApp {
    public final static int CLIENTS = 20;
    public final static int TABLES = 5;

    public static void main(String argv[]) throws InterruptedException {


        List<Thread> threads = new ArrayList<>();
        for(int i = 0; i < CLIENTS; ++i) {
            threads.add(new Thread(new Client(i, pm)));
        }

        threads.forEach(Thread::start);

        for (Thread thread : threads) {
            thread.join();
        }
    }

}
