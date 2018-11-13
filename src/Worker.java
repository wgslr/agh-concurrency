import java.util.Random;
import java.util.function.Consumer;

public class Worker implements Runnable {

    private final long Loops;
    private final long M;
    private final Consumer<Long> action;
    private final Random randomizer = new Random();

    public Worker(Consumer<Long> action, long M, long Loops) {
        this.action = action;
        this.M = M;
        this.Loops = Loops;
    }

    @Override
    public void run() {
        if(Loops  < 0) {
            while(true){
                long portion = randomizer.longs(1,1, M  + 1).findFirst().getAsLong();
                action.accept(portion);
            }
        }else{
            for (int i = 0 ; i < Loops; ++i){
                long portion = randomizer.longs(1,1, M  + 1).findFirst().getAsLong();
                action.accept(portion);
            }
        }
    }
}
