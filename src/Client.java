import com.sun.org.apache.xml.internal.serialize.Printer;

public class Client implements Runnable {

    private int id;
    private PrinterMonitor pm;

    public Client(int id, PrinterMonitor pm) {
        this.id = id;
        this.pm = pm;
    }

    public void run() {

        while (true) {
            Delayer.randomDelay(100, 1000);

            PrinterTask t = new PrinterTask(1500,
                    "Printed document reqeuested Anno Domini " + java.time.LocalDateTime.now());
            int printerId = 0;

            try {
                System.out.println("Client " + id + " waits for a printer");
                printerId = pm.reserve();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            print(printerId, t);
            pm.free(printerId);

            Delayer.randomDelay(3000);
        }

    }

    private void print(int printerId, PrinterTask task) {
        System.out.println("Client " + id + " started printing on printer " + printerId + " of '" + task.content +
                "'");
        Delayer.wait(task.durationMs);
        System.out.println("Client " + id + " finished printing on printer " + printerId + " of " +
                "'" + task.content + "'");
    }
}
