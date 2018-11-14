import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Canvas extends JFrame {

    private BufferedImage I;
//    private double zx, zy, cX, cY, tmp;

    public Canvas(int color[][]) {
        super("Mandelbrot Set");

        setBounds(100, 100, 800, 600);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        I = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < color.length; y++) {
            for (int x = 0; x < color[y].length; x++) {
//                zx = zy = 0;
//                cX = (x - 400) / ZOOM;
//                cY = (y - 300) / ZOOM;
//                int iter = MAX_ITER;
//                while (zx * zx + zy * zy < 4 && iter > 0) {
//                    tmp = zx * zx - zy * zy + cX;
//                    zy = 2.0 * zx * zy + cY;
//                    zx = tmp;
//                    iter--;
//                }
                int c = color[y][x];
                I.setRGB(x, y, c | (c << 8));
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(I, 0, 0, this);
    }
}
