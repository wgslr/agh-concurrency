import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FairBuffer extends AbstractBuffer {
    private Lock lock = new ReentrantLock();
    private Condition contentChanged = lock.newCondition();
    private AtomicLong nextId = new AtomicLong();

    private Queue<Long> putClients = new LinkedList<>();
    private Queue<Long> getClients = new LinkedList<>();

    public FairBuffer(long size) {
        super(size);
    }

    @Override
    public void do_put(long portions) {
        lock.lock();

        try {
            Long id = makeOperationId();
            putClients.add(id);

            // when there is less than M space used always allow insert
            while (freeSpace() < portions) {
                while (!(id.equals(getClients.peek()) || contentCount < size / 2)) {
                    if (!contentChanged.await(ProdConsApp.LOCK_TIMEOUT_SECONDS, TimeUnit.SECONDS)) {
                        return;
                    }
                }
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
            Long id = makeOperationId();
            getClients.add(id);

            while (contentCount < portions) {
                while (!(id.equals(getClients.peek()) || contentCount > size / 2)) {
                    if (!contentChanged.await(ProdConsApp.LOCK_TIMEOUT_SECONDS, TimeUnit.SECONDS)) {
                        return;
                    }
                }
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
        return "FairBuffer";
    }


    private Long makeOperationId() {
        return nextId.getAndIncrement();
    }
}
