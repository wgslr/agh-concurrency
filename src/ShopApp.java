import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class ShopApp {
    final static int CUSTOMERS = 50;
    final static int BASKETS = 5;

    public static void main(String args[]) throws InterruptedException {
        Shop s = new Shop(BASKETS);
        List<Thread> customers = new ArrayList<>();
        Random delayGenerator = new Random();

        for (int i = 0; i < CUSTOMERS; ++i) {
            Thread t = new Thread(new Customer(s));
            customers.add(t);
        }

        customers.forEach(Thread::start);

        for (Thread customer : customers) {
            customer.join();
        }
    }
}
