package pl.edu.pw.mini.taio.omino.lib.generators;

import pl.edu.pw.mini.taio.omino.core.Block;

import java.util.Collection;

public interface BlockGenerator {
    Block generate();
    Collection<Block> generate(int count);
    Collection<Block> all();
}
