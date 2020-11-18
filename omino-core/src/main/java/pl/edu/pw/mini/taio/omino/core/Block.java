package pl.edu.pw.mini.taio.omino.core;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Block implements Comparable<Block> {
    private final Collection<Pixel> pixels;
    private final int size;
    private final int width;
    private final int height;

    private Identifier id;
    private Collection<Block> rotations;
    private Color color;

    public Block(Block other) {
        this(other, other.color);
    }

    public Block(Block other, Color color) {
        this(other.pixels.stream(), color);
    }

    public Block(Stream<Pixel> pixels) {
        this(pixels, null);
    }

    public Block(Stream<Pixel> pixels, Color color) {
        this.pixels = Collections.unmodifiableCollection(
                pixels
                .map(Pixel::new)
                .collect(Collectors.toCollection(TreeSet::new)));
        this.color = color;
        this.size = this.pixels.size();
        normalize();
        this.width = this.pixels.stream().mapToInt(Pixel::getX).max().orElse(-1) + 1;
        this.height = this.pixels.stream().mapToInt(Pixel::getY).max().orElse(-1) + 1;
    }

    public void normalize() {
        int minX = pixels.stream().mapToInt(Pixel::getX).min().orElse(0);
        int minY = pixels.stream().mapToInt(Pixel::getY).min().orElse(0);
        pixels.forEach(pixel -> pixel.normalize(minX, minY));
    }

    public Collection<Pixel> getPixels() {
        return pixels;
    }

    public Color getColor() {
        if(color == null) color = new Color(hashCode());
        return color;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getSize() {
        return size;
    }

    public Block getRotated90() {
        return new Block(pixels.stream()
                .map(Pixel::rotated90), color);
    }

    public Block getRotated180() {
        return new Block(pixels.stream()
                .map(Pixel::rotated180), color);
    }

    public Block getRotated270() {
        return new Block(pixels.stream()
                .map(Pixel::rotated270), color);
    }

    public Collection<Block> getRotations() {
        if(rotations == null) {
            rotations = new LinkedList<>();
            rotations.add(this);
            List<Supplier<Block>> rotators = List.of(this::getRotated90, this::getRotated180, this::getRotated270);
            for (Supplier<Block> rotator : rotators) {
                Block rotation = rotator.get();
                if (rotations.stream().noneMatch(block -> block.pixels.containsAll(rotation.pixels)))
                    rotations.add(rotation);
                else break;
            }
            for (Block rotation : rotations) rotation.id = this.id;
        }
        return rotations;
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
    @SuppressWarnings("NullableProblems")
    public int compareTo(Block other) {
        if(other == null) throw new NullPointerException();
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
