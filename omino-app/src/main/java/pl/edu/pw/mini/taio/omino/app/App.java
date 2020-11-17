package pl.edu.pw.mini.taio.omino.app;

import pl.edu.pw.mini.taio.omino.lib.generators.BlockGenerator;
import pl.edu.pw.mini.taio.omino.lib.generators.IncrementalBlockGenerator;

public class App {
    public static void main(String[] args) {
        for (int i = 1; i <= 16; i++) {
            long start = System.nanoTime();
            BlockGenerator generator = new IncrementalBlockGenerator(i, 123);
            long end = System.nanoTime();
            System.out.printf("Generated %d block%s of size %d in %.6fs%n",
                    generator.count(),
                    generator.count() != 1 ? 's' : "",
                    i,
                    (double)(end - start)/1_000_000_000);
        }
    }
}
