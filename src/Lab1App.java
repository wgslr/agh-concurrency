import com.sun.org.apache.regexp.internal.RE;

public class Lab1App {
    final static long REPEATS = 100000000;

    public static void main(String args[]) throws InterruptedException {
        Counter c = new Counter();
        System.out.println("Initial counter value: " + c.getValue());

        Thread increaser = new Thread(() -> {
            for(long i = 0; i < REPEATS; ++i) {
                c.increase();
            }
        });
        Thread decreaser = new Thread(() -> {
            for(long i = 0; i < REPEATS; ++i) {
                c.decrease();
            }
        });

        System.out.println("Looping " + REPEATS + " times...");

        increaser.start();
        decreaser.start();

        increaser.join();
        decreaser.join();

        System.out.println("Final counter value: " + c.getValue());
    }
}
