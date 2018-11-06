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
    private int sittingCount = 0;

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

            // allows transition state when first pair member sits but the pair
            // is not popped from queue as its waiting for the second one - this one
            while (!(
                    pairId.equals(pairsWaiting.peek()) &&
                            (pairSitting == null || pairId.equals(pairSitting)))
            ) {
                myCond.await();
            }

            myCond.signalAll();

            sit(pairId);

            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        } finally {
            lock.unlock();
        }
    }

    private void sit(Integer pairId) {
        pairSitting = pairId;
        sittingCount++;
        assert sittingCount <= 2;

        if (sittingCount == 2) {
            Integer popped = pairsWaiting.remove();
            pairToCond.remove(pairId);

            assert pairId.equals(popped);
        }
    }

    // @fixme ensure only sitter leaves?
    public void leave(Integer pairId) {
        lock.lock();
        try {
            if(!pairId.equals(pairSitting)) {
                throw new RuntimeException("Cannot leave when other pair is sitting!");
            }

            --sittingCount;

            assert sittingCount >= 0;
            if (sittingCount == 0) {
                pairSitting = null;
                Integer nextPair = pairsWaiting.peek();
                if (nextPair != null) {
                    pairToCond.get(nextPair).signalAll();
                }
            }
        } finally {
            lock.unlock();
        }

    }
}

