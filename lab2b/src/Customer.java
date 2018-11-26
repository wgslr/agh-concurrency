import java.util.Random;

public class Customer implements Runnable {

    private static int lastId = 0;

    private int id;
    private Shop shop;
    private Random random;

    public Customer(Shop shop) {
        this.shop = shop;
        id = ++lastId;
        this.random = new Random();
    }

    @Override
    public void run() {
        try {
            Thread.sleep(random.nextInt(500));
        } catch (InterruptedException e) {
        }

        System.out.println("Customer " + id + " waiting for basket");

        shop.provideBasket();
        doShopping();

        System.out.println("Customer " + id + " finished shopping");
        shop.reclaimBasket();
    }

    private void doShopping() {
        int delay = random.nextInt(1_000);
        System.out.println("Customer " + id + " shopping for " + delay + "ms");
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
        }
    }

}
