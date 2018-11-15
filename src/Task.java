public class Task implements Runnable {

    public static class Area {
        public final int minX, maxX;
        public final int minY, maxY;

        public Area(int minX, int maxX, int minY, int maxY) {
            this.minX = minX;
            this.maxX = maxX;
            this.minY = minY;
            this.maxY = maxY;
        }
    }

    private final Area area;
    private int colors[][];

    public Task(Area a, int colors[][]) {
        area = a;
        this.colors = colors;
    }

    @Override
    public void run() {
        for (int x = area.minX; x <= area.maxX; ++x){
            for (int y = area.minY; y <= area.maxY; ++y){
                colors[y][x] = Mandelbrot.colorForCoords(x,y);
            }
        }
    }

}
