import java.util.Random;

/**
 * Handles waiting for random delays.
 */
public class Delayer {
    private static Random generator = new Random();

    public static boolean randomDelay(int maxMs) {
        return randomDelay(0, maxMs);
    }

    /**
     * Waits for a random number of milliseconds.
     * @return False is randomDelay was interrupted, true otherwise
     */
    public static boolean randomDelay(int minMs, int maxMs) {
        int range = maxMs - minMs;
        int delay = minMs + generator.nextInt(range);
        return wait(delay);
    }

    public static boolean wait(int delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            return false;
        }
        return true;

    }

}
