public class Buffer {
    String value;
    Semaphore writeSem;
    Semaphore readSem;

    public Buffer() {
        value = null;
        this.writeSem = new Semaphore(true);
        this.readSem = new Semaphore(false);
    }

    public void put(String str) {
        writeSem.P();

        value = str;

        readSem.V();
    }

    public String take() {
        readSem.P();

        String v = value;
        value = null;

        writeSem.V();
        return v;
    }
}
