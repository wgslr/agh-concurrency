import java.util.Iterator;

public class ColorMatrix implements Iterable<ColorMatrix.Coord> {

    public class Coord {
        public final int continuous;

        public Coord(int continuous) {
            this.continuous = continuous;
        }

        public Coord(int x, int y) {
            continuous = width * y + x;
        }


        public int getX() {
            return continuous % width;
        }

        public int getY() {
            return continuous / width;
        }

        public int getValue() {
            return get(getX(), getY());
        }


        public Coord next() {
            return new Coord(continuous + 1);
        }

    }


    public final int width;
    public final int height;
    private int colors[][];

    public ColorMatrix(int width, int height) {
        this.width = width;
        this.height = height;
        this.colors = new int[height][width];
    }

    public Coord getCoord(int x, int y) {
        return new Coord(x, y);
    }

    public Coord getCoord(int continous) {
        return new Coord(continous);
    }

    public int get(int x, int y) {
        return colors[y][x];
    }

    public int getLength() {
        return width * height;
    }

    public void set(int x, int y, int value) {
        colors[y][x] = value;
    }

    @Override
    public Iterator<Coord> iterator() {
        return new CoordSpan(new Coord(0, 0), width * height).iterator();
    }
}
