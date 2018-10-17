import java.util.Random;

public class Customer implements Runnable {

    private static int lastId = 0;

    private int id;
    private Shop shop;

    public Customer(Shop shop) {
        this.shop = shop;
        id = ++lastId;
    }

    @Override
    public void run() {
        System.out.println("Customer " + id + " waiting for basket");
        shop.provideBasket();

        doShopping();

        System.out.println("Customer " + id + " finished shopping");
        shop.reclaimBasket();
    }

    private void doShopping() {
        Random delayGenerator = new Random();

        System.out.println("Customer " + id + " shopping");
        try {
            Thread.sleep(delayGenerator.nextInt(5_000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
