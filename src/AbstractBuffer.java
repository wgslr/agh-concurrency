public abstract class AbstractBuffer {
    int size;

    public AbstractBuffer(int size){
        this.size = size;
    }

    public abstract void put(int portions);

    public abstract void get(int portions);

    public abstract String name();
}
