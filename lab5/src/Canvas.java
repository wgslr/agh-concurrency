import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Canvas extends JFrame {

    private BufferedImage I;

    public Canvas(ColorMatrix colors) {
        super("Mandelbrot");

        setBounds(100, 100, 800, 600);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        I = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);

        for (ColorMatrix.Coord c : colors) {
            int x = c.getX();
            int y = c.getY();
            int val = c.getValue();
            I.setRGB(x, y, val | (val << 8));
        }
    }


    @Override
    public void paint(Graphics g) {
        g.drawImage(I, 0, 0, this);
    }
}
