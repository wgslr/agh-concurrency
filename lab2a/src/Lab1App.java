import com.sun.org.apache.regexp.internal.RE;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class Lab1App {
    final static long REPEATS = 100000000;

    public final static long MESSAGES = 100;
    public final static long CONSUMERS = 5;
    public final static long MSG_PER_CONS = 20;

    public static void main(String args[]) throws InterruptedException {
        System.out.println("Starting producer and consumer");
        Buffer b = new Buffer();
        Thread prod = new Thread(new Producer(b));
        prod.start();

        List<Thread> threads = new ArrayList<>();
        for(long i = 0; i < CONSUMERS; ++i) {
            threads.add(new Thread(new Consumer(b, i)));
        }

        threads.forEach(Thread::start);

        prod.join();
        System.out.println("Producer finished");
    }
}
