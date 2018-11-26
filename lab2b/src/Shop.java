public class Shop {
    private Semaphore sem;

    public Shop(int baskets) {
        this.sem = new Semaphore(baskets);
    }

    public void provideBasket() {
        sem.P();
    }

    public void reclaimBasket() {
        sem.V();
    }

}
