package pl.edu.pw.mini.taio.omino.lib.generators;

import java.util.Random;

public abstract class FixedSizedBlockGenerator implements BlockGenerator {

    protected final Random random;
    protected final int size;

    public FixedSizedBlockGenerator(int size) {
        this(size, new Random().nextLong());
    }

    public FixedSizedBlockGenerator(int size, long seed) {
        if(size < 0) throw new IllegalArgumentException("Block size must be a positive integer!");
        this.random = new Random(seed);
        this.size = size;
    }

}
