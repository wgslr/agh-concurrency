public class MandelbrotApp {
    public static void main(String argv[]) {
//
        int color[][] = new int[500][500];
        System.out.println(color.length);
        for (int y = 0; y < color.length; y++) {
            for (int x = 0; x < color[y].length; x++) {
                color[y][x] = x * y % 255;
            }
        }

        new Canvas(color).setVisible(true);
    }
}
