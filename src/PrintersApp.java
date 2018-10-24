import java.util.ArrayList;
import java.util.List;

public class PrintersApp {
    public final static int CLIENTS = 8;
    public final static int PRINTERS = 3;

    public static void main(String argv[]) throws InterruptedException {
        PrinterMonitor pm = new PrinterMonitor(PRINTERS);


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
