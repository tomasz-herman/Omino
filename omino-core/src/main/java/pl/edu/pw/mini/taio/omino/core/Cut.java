package pl.edu.pw.mini.taio.omino.core;

public class Cut {
    private final Pixel x, y;

    public Cut(Pixel x, Pixel y) {
        if(!x.isNeighbor(y)) throw new IllegalArgumentException("Pixels are not neighbouring in a cut!");
        this.x = x;
        this.y = y;
    }

    public Pixel getX() {
        return x;
    }

    public Pixel getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cut cut = (Cut) o;

        return cut.x.equals(x) && cut.y.equals(y) || cut.x.equals(y) && cut.y.equals(x);
    }

    @Override
    public int hashCode() {
        return x.hashCode() + y.hashCode();
    }

    @Override
    public String toString() {
        return String.format("[%s|%s]", x, y);
    }
}
