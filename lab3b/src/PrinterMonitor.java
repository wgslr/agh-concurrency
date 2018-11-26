import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PrinterMonitor {

    private final Lock lock = new ReentrantLock();
    private final Condition canReserve = lock.newCondition();
//    final Condition canReturn = lock.newCondition();

    private Boolean inUse[];
    private int freeCount;

    public PrinterMonitor(int printerCount) {
        inUse = new Boolean[printerCount];
        for (int i = 0; i < printerCount; ++i) {
            inUse[i] = false;
        }
        freeCount = printerCount;
    }

    public int reserve() throws InterruptedException {
        lock.lock();
        try {
            while (freeCount == 0) {
                canReserve.await();
            }

            for (int i = 0; i < inUse.length; ++i) {
                if (!inUse[i]) {
                    inUse[i] = true;
                    freeCount--;
                    return i;
                }
            }

            throw new RuntimeException("Could not find printer despite flag being set");

        } finally {
            lock.unlock();
        }

    }

    public void free(int printerId) {
        lock.lock();
        try {
            inUse[printerId] = false;
            freeCount++;
            canReserve.signal();
        } finally {
            lock.unlock();
        }
    }
}
