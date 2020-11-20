package pl.edu.pw.mini.taio.omino.lib.generators;

import pl.edu.pw.mini.taio.omino.core.Block;
import pl.edu.pw.mini.taio.omino.core.Pixel;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RandomBlockGenerator extends FixedSizedBlockGenerator {
    private static final List<List<Integer>> MOVES =
            List.of(List.of(1, 0), List.of(-1, 0), List.of(0, 1), List.of(0, -1));

    private int count = -1;

    public RandomBlockGenerator(int size) {
        this(size, new Random().nextLong());
    }

    public RandomBlockGenerator(int size, long seed) {
        super(size, seed);
    }

    @Override
    public Block any() {
        if(size == 0) return new Block(Stream.of());
        List<Pixel> pixels = new ArrayList<>(size);
        pixels.add(new Pixel(0, 0));
        while(pixels.size() != size) {
            List<Pixel> possiblePixels = pixels.stream()
                    .flatMap(pixel -> MOVES.stream()
                            .map(move -> new Pixel(pixel.getX() + move.get(0), pixel.getY() + move.get(1))))
                    .distinct()
                    .filter(pixel -> !pixels.contains(pixel))
                    .collect(Collectors.toList());
            pixels.add(possiblePixels.get(random.nextInt(possiblePixels.size())));
        }
        return new Block(pixels.stream());
    }

    @Override
    public Block get(int i) {
        return new RandomBlockGenerator(size, i).any();
    }

    @Override
    public Stream<Block> many() {
        return Stream.generate(this::any);
    }

    @Override
    public Stream<Block> all() {
        return many().distinct().limit(count());
    }

    @Override
    public int count() {
        if(count == -1) count = new IncrementalBlockGenerator(size).count();
        return count;
    }
}
