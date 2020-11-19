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
    private List<Collection<Collection<Block>>> cutBlocks;

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

    public void setColor(Color color) {
        this.color = color;
        if(rotations != null) {
            for (Block rotation : rotations) {
                rotation.color = color;
            }
        }
        if(cutBlocks != null) {
            for (Collection<Collection<Block>> cutBlock : cutBlocks) {
                for (Collection<Block> blocks : cutBlock) {
                    for (Block block : blocks) {
                        block.color = color;
                    }
                }
            }
        }
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
            rotations = new ArrayList<>();
            rotations.add(this);
            List<Supplier<Block>> rotators = List.of(this::getRotated90, this::getRotated180, this::getRotated270);
            for (Supplier<Block> rotator : rotators) {
                Block rotation = rotator.get();
                if (rotations.stream().noneMatch(block -> block.pixels.containsAll(rotation.pixels)))
                    rotations.add(rotation);
                else break;
            }
            for (Block rotation : rotations) {
                rotation.id = this.id;
                rotation.rotations = this.rotations;
            }
        }
        return rotations;
    }

    public Collection<Collection<Block>> getCutBlocks(int cuts) {
        if(cutBlocks == null) calculateCutBlocks();
        if (cuts >= cutBlocks.size())
            return null;
        return cutBlocks.get(cuts);
    }

    private void calculateCutBlocks() {
        cutBlocks = new ArrayList<>();
        List<Cut> possibleCuts = getPossibleCuts();
        boolean[] onOffCutList = new boolean[possibleCuts.size()];
        for (int i = 0; i <= possibleCuts.size(); i++) {
            cutBlocks.add(new ArrayList<>());
        }
        calculateCutBlocksRec(possibleCuts, onOffCutList, 0);
    }

    private void calculateCutBlocksRec(List<Cut> possibleCuts, boolean[] onOffCutList, int level) {

        List<Cut> cuts = new ArrayList<>();
        for (int i = 0; i < onOffCutList.length; i++) {
            if (onOffCutList[i]) cuts.add(possibleCuts.get(i));
        }
        List<Block> blocks = split(cuts);
        blocks.sort(Comparator.naturalOrder());
        if (!cutBlocks.get(cuts.size()).contains(blocks)) {
            cutBlocks.get(cuts.size()).add(blocks);
        }

        if (level == possibleCuts.size()) return;
        onOffCutList[level] = true;
        calculateCutBlocksRec(possibleCuts, onOffCutList,level + 1);
        onOffCutList[level] = false;
        calculateCutBlocksRec(possibleCuts, onOffCutList,level + 1);
    }

    private List<Block> split(List<Cut> cuts) {
        List<Pixel> pixelList = new ArrayList<>(pixels);
        List<Block> split = new ArrayList<>();
        boolean[] marked = new boolean[pixels.size()];
        int marks = 0;

        while(marks < pixels.size()) {
            List<Pixel> temp = new ArrayList<>();
            for (int i = 0; i < marked.length; i++) {
                if (!marked[i]) {
                    marked[i] = true;
                    temp.add(pixelList.get(i));
                    marks++;
                    break;
                }
            }
            for (Pixel __ : pixelList) {
                for (Pixel pixel : pixelList) {
                    if(!temp.contains(pixel)) {
                        if(temp.stream().anyMatch(pix -> pix.isNeighbor(pixel) && !cuts.contains(new Cut(pixel, pix)))) {
                            temp.add(pixel);
                            marked[pixelList.indexOf(pixel)] = true;
                            marks++;
                        }
                    }
                }
            }
            split.add(new Block(temp.stream(), color));
        }

        return split;
    }

    private List<Cut> getPossibleCuts() {
        List<Cut> cuts = new ArrayList<>();
        for (Pixel x : pixels) {
            for (Pixel y : pixels) {
                if(x.compareTo(y) > 0) {
                    if(x.isNeighbor(y)) cuts.add(new Cut(x, y));
                }
            }
        }
        return cuts;
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
        builder.append("\n");
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                builder.append(arr[i][j] ? "██" : "  ");
            }
            builder.append("\n");
        }

        return builder.toString();
    }
}
