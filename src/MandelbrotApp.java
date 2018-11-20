import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Math.min;

public class MandelbrotApp {


    public final static int WIDTH = 800;
    public final static int HEIGHT = 600;
    public final static boolean DISPLAY = false;

    public static class TestCase {
        public final int threads;
        public final int tasks;

        public TestCase(int threads, int tasks) {
            this.threads = threads;
            this.tasks = tasks;
        }

        public void run() throws InterruptedException {

            ColorMatrix cm = new ColorMatrix(WIDTH, HEIGHT);
            ExecutorService es = Executors.newFixedThreadPool(threads);


            List<Task> tasksList = makeSpans(cm, tasks).stream()
                    .map(span -> new Task(cm, span))
                    .collect(Collectors.toList());

            System.out.println(String.format("Start %d threads %d tasks", threads, tasks));
            long start = System.nanoTime();

            tasksList.stream().forEach(es::submit);
            es.shutdown();
            es.awaitTermination(10000, TimeUnit.MINUTES);

            long end = System.nanoTime();

            System.out.println(String.format("%d;\t%d;\t%d", threads, tasks, end - start));

            if (DISPLAY) {
                Canvas c = new Canvas(cm);
                c.setVisible(true);
            }

            es.shutdown();
        }
    }


    public static void main(String argv[]) throws InterruptedException {
        if (argv.length == 0) {
            List<TestCase> tests = new ArrayList<>();

            for (int threads : new int[]{1, 4, 8}) {
                for (int tasks : new int[]{threads, 10 * threads, WIDTH * HEIGHT}) {
                    tests.add(new TestCase(threads, tasks));
                }
            }

            for (TestCase test : tests) {
                test.run();
            }
        } else {
            int threads = Integer.parseInt(argv[0]);
            int tasks = Integer.parseInt(argv[1]);
            TestCase tc = new TestCase(threads, tasks);
            tc.run();
        }
    }


    private static List<CoordSpan> makeSpans(ColorMatrix cm, int count) {
        List<CoordSpan> acc = new ArrayList<>();
        int length, toDispense;
        length = toDispense = cm.getLength();
        int pos = 0;
        while (pos < length) {
            assert count > 0; // sanity check
            int spanLength = min(toDispense / count, length - pos);

            acc.add(new CoordSpan(cm.getCoord(pos), spanLength));

            toDispense -= spanLength;
            pos += spanLength;
            --count;
        }
        return acc;
    }

}
