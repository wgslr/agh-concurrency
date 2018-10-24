import javafx.util.Pair;

import java.util.HashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Waiter {

    private final Lock lock = new ReentrantLock();

    Condition tableFreeCond = lock.newCondition();


    HashMap<Integer, Condition> pairToCond;
    HashMap<Integer, Integer> pairToCount;

    public Waiter() {

    }

    // TODO timeout
    public boolean requestTable(Integer pairId) {
        // @FIXME doesn't work when table is taken and both people are present

        lock.lock();

        try {
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

