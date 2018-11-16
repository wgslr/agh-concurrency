import java.awt.Graphics;
import javax.swing.JFrame;

public class Mandelbrot extends JFrame {
    private static final int MAX_ITER = 5700;
    private static final double ZOOM = 200;

    public static int colorForCoords(int x, int y) {
        double zx, zy, cX, cY, tmp;

        zx = zy = 0;
        cX = (x - 500) / ZOOM;
        cY = (y - 300) / ZOOM;
        int iter = MAX_ITER;
        while (zx * zx + zy * zy < 4 && iter > 0) {
            tmp = zx * zx - zy * zy + cX;
            zy = 2.0 * zx * zy + cY;
            zx = tmp;
            iter--;
        }
        return iter;
    }
}
