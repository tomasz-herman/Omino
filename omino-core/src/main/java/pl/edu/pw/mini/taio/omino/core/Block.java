package pl.edu.pw.mini.taio.omino.core;

import java.awt.*;
import java.util.*;
import java.util.stream.Collectors;

public class Block implements Comparable<Block> {
    private final Collection<Pixel> pixels;
    private Identifier id;
    private int width, height;
    private final Color color;


    public Block(Collection<Pixel> pixels) {
        this(pixels, null);
    }

    public Block(Collection<Pixel> pixels, Color color) {
        this.pixels = pixels.stream()
                .map(Pixel::new)
                .collect(Collectors.toCollection(TreeSet::new));
        this.color = color;
        normalize();
    }

    public void normalize() {
        int minX = pixels.stream().mapToInt(Pixel::getX).min().orElse(0);
        int minY = pixels.stream().mapToInt(Pixel::getY).min().orElse(0);
        pixels.forEach(pixel -> pixel.normalize(minX, minY));
        width = pixels.stream().mapToInt(Pixel::getX).max().orElse(-1) + 1;
        height = pixels.stream().mapToInt(Pixel::getY).max().orElse(-1) + 1;
    }

    public Collection<Pixel> getPixels() {
        return pixels;
    }

    public Color getColor() {
        return color;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Block getRotated90() {
        return new Block(pixels.stream()
                .map(Pixel::rotated90)
                .collect(Collectors.toList()), color);
    }

    public Block getRotated180() {
        return new Block(pixels.stream()
                .map(Pixel::rotated180)
                .collect(Collectors.toList()), color);
    }

    public Block getRotated270() {
        return new Block(pixels.stream()
                .map(Pixel::rotated270)
                .collect(Collectors.toList()), color);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;

        Block block = (Block) other;

        return compareTo(block) == 0;
    }

    @Override
    public int hashCode() {
        if(id == null) id = new Identifier(this);
        return id.hashCode();
    }

    @Override
    public int compareTo(Block other) {
        if(id == null) id = new Identifier(this);
        if(other.id == null) other.id = new Identifier(other);
        return id.compareTo(other.id);
    }

    @Override
    public String toString() {
        boolean[][] arr = new boolean[height][width];
        pixels.forEach(p -> arr[p.getY()][p.getX()] = true);

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                builder.append(arr[i][j] ? "██" : "  ");
            }
            builder.append("\n");
        }

        return builder.toString();
    }
}
