package pl.edu.pw.mini.taio.omino.lib.generators;

import pl.edu.pw.mini.taio.omino.core.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class VaryingSizeBlockGenerator implements BlockGenerator {
    private final Random random;
    private final List<BlockGenerator> generators;
    private int count = -1;

    public VaryingSizeBlockGenerator(int from, int to, BlockGeneratorFactory factory) {
        this(from, to, factory, new Random().nextLong());
    }

    public VaryingSizeBlockGenerator(int from, int to, BlockGeneratorFactory factory, long seed) {
        if(from < 0 || to < 0 || to < from) throw new IllegalArgumentException("Illegal values of from or to parameters");
        generators = new ArrayList<>(to - from + 1);
        random = new Random(seed);
        for (int i = from; i <= to; i++) {
            BlockGenerator generator = factory.newInstance(i, random.nextLong());
            if(generator == null) throw new IllegalArgumentException("Got null generator from factory!");
            generators.add(generator);
        }

    }

    @Override
    public Block any() {
        BlockGenerator generator = generators.get(random.nextInt(generators.size()));
        return generator.any();
    }

    @Override
    public Block get(int i) {
        BlockGenerator generator = generators.get(Integer.remainderUnsigned(i, generators.size()));
        return generator.get(i);
    }

    @Override
    public Stream<Block> many() {
        return Stream.generate(this::any);
    }

    @Override
    public Stream<Block> all() {
        return generators.stream()
                .flatMap(BlockGenerator::all);
    }

    @Override
    public int count() {
        if(count == -1) {
            count = generators.stream()
                    .mapToInt(BlockGenerator::count)
                    .sum();
        }
        return count;
    }
}
