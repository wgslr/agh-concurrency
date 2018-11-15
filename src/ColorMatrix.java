public class ColorMatrix {

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

        public Coord next() {
            return new Coord(continuous + 1);
        }

    }


    int width;
    int height;
    int colors[][];

    public ColorMatrix(int width, int height) {
        this.width = width;
        this.height = height;
        this.colors = new int[height][width];
    }

    public void set(int x, int y, int value) {
        colors[y][x] = value;
    }
}
