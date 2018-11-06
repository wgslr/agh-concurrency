import javafx.util.Pair;

import java.util.HashMap;
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

    Condition tableFreeCond = lock.newCondition();


    HashMap<Integer, Condition> pairToCond;
    HashMap<Integer, Integer> pairToCount;

    int sitting = 0;

    public Waiter() {

    }

    // TODO timeout
    public boolean requestTable(Integer pairId) {
        // @FIXME doesn't work when table is taken and both people are present

        lock.lock();

        try {
            pairToCond.computeIfAbsent(pairId, x -> lock.newCondition());
            Condition myCond = pairToCond.get(pairId);

            while(sitting > 0) {
                try {
                    myCond.await();
                } catch (InterruptedException e) {
                    return false;
                }
            }

            if (pairToCond.containsKey(pairId)) {
                Condition partner = pairToCond.remove(pairId);
                newcomer = pairId;
                partner.notify();
                return true;
            } else {

                Condition myCond = lock.newCondition();
                pairToCond.put(pairId, myCond);
                while (newcomer != pairId) {
                    try {
                        myCond.await();
                    } catch (InterruptedException e) {
                        return false;
                    }
                }
                return true;
            }
        } finally {
            lock.unlock();
        }
    }
}

