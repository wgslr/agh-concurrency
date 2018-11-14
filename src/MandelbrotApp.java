public class MandelbrotApp {
    public static void main(String argv[]) {
//
        int color[][] = new int[600][800];
        System.out.println(color.length);
        for (int y = 0; y < color.length; y++) {
            for (int x = 0; x < color[y].length; x++) {
                color[y][x] = Mandelbrot.colorForCoords(x,y);
            }
        }

        new Canvas(color).setVisible(true);
    }
}
