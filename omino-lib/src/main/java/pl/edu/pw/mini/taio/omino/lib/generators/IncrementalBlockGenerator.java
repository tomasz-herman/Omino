package pl.edu.pw.mini.taio.omino.lib.generators;

import pl.edu.pw.mini.taio.omino.core.Block;
import pl.edu.pw.mini.taio.omino.core.Pixel;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Generator designed to pre-generate all distinct existing blocks of given size.
 */
public class IncrementalBlockGenerator extends FixedSizedBlockGenerator {

    private static final Set<Block> INITIAL =
            Set.of(new Block(Stream.of(new Pixel(0, 0))));
    private static final List<List<Integer>> MOVES =
            List.of(List.of(1, 0), List.of(-1, 0), List.of(0, 1), List.of(0, -1));

    private final List<Block> all;

    /**
     * Creates new instance of block generator with random seed.
     * All distinct existing blocks of given size are generated in constructor.
     * Since number of blocks grows exponentially with block size this constructor might take a while.
     * Also worth noticing is the size of collection of all blocks.
     * There are more than million of distinct blocks with size greater than 14
     * @param size block size
     */
    public IncrementalBlockGenerator(int size) {
        this(size, new Random().nextLong());
    }

    /**
     * Creates new instance of block generator.
     * All distinct existing blocks of given size are generated in constructor.
     * Since number of blocks grows exponentially with block size this constructor might take a while.
     * Also worth noticing is the size of collection of all blocks.
     * There are more than million of distinct blocks with size greater than 14
     * @param size block size
     * @param seed for random number generation
     */
    public IncrementalBlockGenerator(int size, long seed) {
        super(size, seed);
        if(size == 0) {
            all = List.of(new Block(Stream.of()));
            return;
        }
        Set<Block> set = INITIAL.stream().map(Block::new).collect(Collectors.toSet());
        for (int i = 0; i < size - 1; i++) {
            Set<Block> next = new HashSet<>();
            for (Block block : set) {
                for (Pixel pixel : block.getPixels()) {
                    for (List<Integer> move : MOVES) {
                        Pixel p = new Pixel(pixel.getX() + move.get(0), pixel.getY() + move.get(1));
                        if(block.getPixels().contains(p)) continue;
                        next.add(new Block(Stream.concat(block.getPixels().stream(), Stream.of(p))));
                    }
                }
            }
            set = next;
        }
        all = new ArrayList<>(set);
    }

    @Override
    public Block any() {
        return new Block(all.get(random.nextInt(all.size())));
    }

    @Override
    public Block get(int i) {
        return new Block(all.get(Integer.remainderUnsigned(i, count())));
    }

    @Override
    public Stream<Block> many() {
        return random.ints(0, all.size()).mapToObj(all::get).map(Block::new);
    }

    @Override
    public Stream<Block> all() {
        return all.stream().map(Block::new);
    }

    @Override
    public int count() {
        return all.size();
    }

}
