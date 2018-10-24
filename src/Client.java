import com.sun.org.apache.xml.internal.serialize.Printer;

public class Client implements Runnable {

    private PrinterMonitor pm;

    public Client(PrinterMonitor pm) {
        this.pm = pm;
    }

    public void run() {

        while (true) {
            Delayer.randomDelay(100, 1000);

            PrinterTask t = new PrinterTask(1500,
                    "Printed document reqeuested Anno Domini " + java.time.LocalDateTime.now());
            int printerId = pm.reserve();
            print(printerId, t);
            pm.free(printerId);
        }

    }

    private void print(int printerId, PrinterTask task) {
        System.out.println("Started printing on printer " + printerId + " of '" + task.content + "'");
        Delayer.wait(task.durationMs);
        System.out.println("Finished printing on printer " + printerId + " of '" + task.content + "'");
    }
}
