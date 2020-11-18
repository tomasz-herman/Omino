package pl.edu.pw.mini.taio.omino.core;

public class Cut {
    private final Pixel x, y;

    public Cut(Pixel x, Pixel y) {
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

        return cut.x == x && cut.y == y || cut.x == y && cut.y == x;
    }

    @Override
    public int hashCode() {
        int result = x != null ? x.hashCode() : 0;
        result = 31 * result + (y != null ? y.hashCode() : 0);
        return result;
    }
}
