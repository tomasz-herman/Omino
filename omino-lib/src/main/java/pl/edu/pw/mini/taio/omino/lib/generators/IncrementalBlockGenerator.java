package pl.edu.pw.mini.taio.omino.lib.generators;

import pl.edu.pw.mini.taio.omino.core.Block;
import pl.edu.pw.mini.taio.omino.core.Pixel;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class IncrementalBlockGenerator implements BlockGenerator {

    private static final Set<Block> INITIAL =
            Set.of(new Block(List.of(new Pixel(0, 0))));
    private static final List<List<Integer>> MOVES =
            List.of(List.of(1, 0), List.of(-1, 0), List.of(0, 1), List.of(0, -1));


    private final Random random;
    private final List<Block> all;

    public IncrementalBlockGenerator(int size, long seed) {
        this.random = new Random(seed);
        Set<Block> set = INITIAL;
        for (int i = 0; i < size - 1; i++)
        {
            Set<Block> next = new HashSet<>();
            for (Block block : set) {
                for (Pixel pixel : block.getPixels()) {
                    for (List<Integer> move : MOVES) {
                        Pixel p = new Pixel(pixel.getX() + move.get(0), pixel.getY() + move.get(1));
                        if(block.getPixels().contains(p)) continue;
                        Block b = new Block(block.getPixels());
                        b.getPixels().add(p);
                        b.normalize();
                        next.add(b);
                    }
                }
            }
            set = next;
        }
        all = new ArrayList<>(set);
    }

    @Override
    public Block generate() {
        return all.get(random.nextInt(all.size()));
    }

    @Override
    public Collection<Block> generate(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> generate())
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Block> all() {
        return all;
    }

}
