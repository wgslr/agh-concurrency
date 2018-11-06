import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
First member of a pair arriving is assigned a Condition object
and placed in the pool of waiting lovers.
When the second one comes in their pair id is placed in the queue.
As soon as the table's empty first pair in the queue is placed at the table.
 */

public class Waiter {

    private final Lock lock = new ReentrantLock();

    private HashMap<Integer, Condition> pairToCond = new HashMap<>();
    private Queue<Integer> pairsWaiting = new LinkedList<>();

    private Integer pairSitting = null;

    public Waiter() {

    }

    // TODO timeout
    public boolean requestTable(Integer pairId) {
        lock.lock();

        try {
            Condition myCond;
            if (pairToCond.containsKey(pairId)) {
                // this is second process in a pair
                myCond = pairToCond.get(pairId);
                pairsWaiting.add(pairId);

            } else {
                myCond = lock.newCondition();
                pairToCond.put(pairId, myCond);
            }

            // @fixme do not wait if already available
            while (!(pairSitting == null && pairId.equals(pairsWaiting.peek())
                    || pairId.equals(pairSitting))) {
                myCond.await();
            }

            if (pairSitting == null) {
                // otherwise the partner already arranged sitting
                sit(pairId);
            }

            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        } finally {
            lock.unlock();
        }
    }

    private void sit(Integer pairId) {
        Integer popped = pairsWaiting.remove();
        pairToCond.remove(pairId);
        assert pairId.equals(popped);
        pairSitting = pairId;
    }

    // @fixme ensure only sitter leaves?
    public void leave() {
        lock.lock();
        try {
            pairSitting = null;

            Integer nextPair = pairsWaiting.peek();
            if (nextPair != null) {
                pairToCond.get(nextPair).signalAll();
            }
        } finally {
            lock.unlock();
        }

    }
}

