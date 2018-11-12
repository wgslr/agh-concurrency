import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class Buffer {
    private ArrayList<String> values;
    private ArrayList<Lock> locks;
    private ArrayList<Condition> valueChangedConds;

    public Buffer(int size) {
        values = new ArrayList<>(size);
        locks = new ArrayList<>(size);
        valueChangedConds = new ArrayList<>(size);
        for (int i = 0; i < size; ++i) {
            Lock l = new ReentrantLock();
            values.add("@");
            locks.add(l);
            valueChangedConds.add(l.newCondition());
        }
    }

    public int getSize() {
        return values.size();
    }

    public void waitFor(int position, String expectedVal) {
        Lock lock = locks.get(position);
        lock.lock();

        try {
            Condition cond = valueChangedConds.get(position);
            while (!values.get(position).equals(expectedVal)) {
                cond.await();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void set(int position, String value) {
        Lock lock = locks.get(position);
        lock.lock();

        try {
            Condition cond = valueChangedConds.get(position);
            values.set(position, value);
            cond.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public String toString() {
        return values.stream()
                .map(x -> x.equals("@") ? "_" : x)
                .collect(Collectors.joining());
    }
}
