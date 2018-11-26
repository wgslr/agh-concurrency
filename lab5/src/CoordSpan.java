import java.util.Iterator;
import java.util.NoSuchElementException;

public class CoordSpan implements Iterable<ColorMatrix.Coord> {
    public class CoordIterator implements Iterator<ColorMatrix.Coord> {
        ColorMatrix.Coord current;

        public CoordIterator(ColorMatrix.Coord current) {
            this.current = current;
        }

        @Override
        public boolean hasNext() {
            return current.continuous < first.continuous + length;
        }

        @Override
        public ColorMatrix.Coord next() {
            if (hasNext()) {
                ColorMatrix.Coord toReturn = current;
                current = current.next();
                return toReturn;
            } else {
                throw new NoSuchElementException();
            }
        }
    }


    public final ColorMatrix.Coord first;
    public final int length;

    public CoordSpan(ColorMatrix.Coord first, int length) {
        this.first = first;
        this.length = length;
    }

    @Override
    public Iterator<ColorMatrix.Coord> iterator() {
        return new CoordIterator(first);
    }

}