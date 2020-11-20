package pl.edu.pw.mini.taio.omino.lib.generators;

import pl.edu.pw.mini.taio.omino.core.Block;

import java.util.stream.Stream;

public interface BlockGenerator {
    /**
     * Generates a random block from the generator.
     * @return random block
     */
    Block any();

    /**
     * Generates i-th block from the generator. I-th block is guaranteed to be the same every time.
     * @param i-th block to be taken from the generator
     * @return specific block
     */
    Block get(int i);

    /**
     * Generates infinite stream of random blocks.
     * @return stream of random blocks
     */
    Stream<Block> many();

    /**
     * Generates stream of all available blocks in the generator.
     * @return stream of random blocks
     */
    Stream<Block> all();

    /**
     * How many blocks are in {@link #all()} stream.
     * @return size of {@link #all()} stream
     */
    int count();
}
