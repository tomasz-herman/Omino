package pl.edu.pw.mini.taio.omino.core;

public class Pixel implements Comparable<Pixel>, Cloneable {
    private final int x, y;

    public Pixel(Pixel pixel) {
        this.x = pixel.x;
        this.y = pixel.y;
    }

    public Pixel(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public Pixel normalized(int x, int y) {
        return new Pixel(this.x - x, this.y - y);
    }

    public boolean isNeighbor(Pixel other)
    {
        if (x == other.x && Math.abs(y - other.y) == 1) return true;
        return y == other.y && Math.abs(x - other.x) == 1;
    }

    @SuppressWarnings("SuspiciousNameCombination")
    public Pixel rotated90() {
        return new Pixel(y, -x);
    }

    public Pixel rotated180() {
        return new Pixel(-x, -y);
    }

    @SuppressWarnings("SuspiciousNameCombination")
    public Pixel rotated270() {
        return new Pixel(-y, x);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pixel pixel = (Pixel) o;

        if (x != pixel.x) return false;
        return y == pixel.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 21269 * result + y;
        return result;
    }

    @Override
    public int compareTo(Pixel other) {
        int byX = Integer.compare(x, other.x);
        return byX == 0 ? Integer.compare(y, other.y) : byX;
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", x, y);
    }
}
