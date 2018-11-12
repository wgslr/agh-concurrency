public class Worker implements Runnable {

    public enum Type {
        PRODUCER,
        CONSUMER
    }

    private final long Loops;
    private final long M;
    private final Type t;
    private final AbstractBuffer buffer;

    public Worker(Type t, AbstractBuffer buffer, long M, long Loops) {
        this.t = t;
        this.buffer = buffer;
        this.M = M;
        this.Loops = Loops;
    }

    @Override
    public void run() {
        for (int i = 0 ; i < Loops; ++i){

        }
    }
}
