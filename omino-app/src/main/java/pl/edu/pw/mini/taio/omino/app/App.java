package pl.edu.pw.mini.taio.omino.app;

import pl.edu.pw.mini.taio.omino.core.Block;
import pl.edu.pw.mini.taio.omino.lib.generators.BlockGenerator;
import pl.edu.pw.mini.taio.omino.lib.generators.IncrementalBlockGenerator;

public class App {
    public static void main(String[] args) {
        BlockGenerator generator = new IncrementalBlockGenerator(12, 123);
        for (Block block : generator.all()) {
            System.out.println(block);
        }
    }
}
