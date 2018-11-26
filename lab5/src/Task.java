public class Task implements Runnable {

    private final ColorMatrix colorMatrix;
    private final CoordSpan span;

    public Task(ColorMatrix cm, CoordSpan span) {
        this.colorMatrix = cm;
        this.span = span;
    }

    @Override
    public void run() {
        for (ColorMatrix.Coord c : span) {
            int x = c.getX();
            int y = c.getY();
            colorMatrix.set(x, y, Mandelbrot.colorForCoords(x, y));
        }
    }

}
