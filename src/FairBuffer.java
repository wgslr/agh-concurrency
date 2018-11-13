import sun.awt.image.ImageWatched;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

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
            while (freeSpace() < portions || !(
                    id.equals(putClients.peek()) || contentCount <= size / 2
            )) {

//                System.out.println("Waiting for " + id + ", put queue is " +
//                        putClients.stream()
//                                .map(Objects::toString)
//                                .collect(Collectors.joining(" ")));

                if (!contentChanged.await(ProdConsApp.LOCK_TIMEOUT_SECONDS, TimeUnit.SECONDS)) {
                    System.out.println("Put " + id + " of " + portions + " timeout at queue " +  queueToStr(putClients));
                    return;
                }
            }

            Long popped = getClients.poll();
            System.out.println("Putting cause queue front is " + popped);
            assert id.equals(popped);

            contentCount += portions;
            contentChanged.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private String queueToStr(Queue<Long> l) {
        return l.stream()
                .map(Objects::toString)
                .collect(Collectors.joining(" "));
    }

    @Override
    public void do_get(long portions) {
        lock.lock();

        try {
            Long id = makeOperationId();
            System.out.println("My id is " + id);

            getClients.add(id);
            System.out.println("Added id " + id + " to get queue: " + queueToStr(getClients));


            while (contentCount < portions || !(id.equals(getClients.peek())
                    || contentCount > size / 2
            )) {

//                System.out.println(
//                        "Waiting for " + id + ", get queue is " + queueToStr(getClients));

                if (!contentChanged.await(ProdConsApp.LOCK_TIMEOUT_SECONDS, TimeUnit.SECONDS)) {
                    System.out.println("Get " + id + " of " + portions + " timeout at queue " + queueToStr(getClients));
                    return;
                }
            }

            Long popped = getClients.poll();
            System.out.println("Getting cause queue front is " + popped);
            assert id.equals(popped);

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
        return nextId.incrementAndGet();
    }
}
