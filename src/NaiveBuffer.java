import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NaiveBuffer extends AbstractBuffer {
    private Lock lock = new ReentrantLock();
    private Condition contentChanged = lock.newCondition();

    public NaiveBuffer(long size) {
        super(size);
    }

    @Override
    public void do_put(long portions) {
        lock.lock();

        try {
            while (freeSpace() < portions) {
                contentChanged.await();
            }
            contentCount += portions;
            contentChanged.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void do_get(long portions) {
        lock.lock();

        try {
            while (contentCount < portions) {
                contentChanged.await();
            }
            contentCount -= portions;
            contentChanged.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    @Override
    public String name() {
        return "NaiveBuffer";
    }
}
