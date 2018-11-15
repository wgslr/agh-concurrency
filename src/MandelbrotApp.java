import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MandelbrotApp {


    public static void main(String argv[]) {
        int ys = 600;
        int xs = 800;
        int color[][] = new int[ys][xs];
//        System.out.println(color.length);
//        for (int y = 0; y < color.length; y++) {
//            for (int x = 0; x < color[y].length; x++) {
//                color[y][x] = Mandelbrot.colorForCoords(x, y);
//            }
//        }

        long start = System.nanoTime();

        int tasksCnt = 2;
        int tasksCntSq = 4;
        List<Future<?>> results = new LinkedList<>();

        ExecutorService es = Executors.newFixedThreadPool(tasksCnt);


        for (int i = 0; i < tasksCnt; ++i) {
            Task.Area a = new Task.Area(i * (xs / 2), (i + 1) * (xs / 2) - 1,
                    i * (ys / 2), (i + 1) * (ys / 2) - 1);
            Task t = new Task(a, color);

            results.add(es.submit(t));
        }

        results.forEach(f -> {
            try {
                f.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });

        long end = System.nanoTime();

        System.out.println(end - start);

        new Canvas(color).setVisible(true);
    }

}
