public class Buffer {
    String value;

    public void put(String str) {
        value = str;
    }

    public String take() {
        String v = value;
        value = null;
        return v;
    }
}
