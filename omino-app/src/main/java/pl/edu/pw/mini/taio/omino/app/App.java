package pl.edu.pw.mini.taio.omino.app;

import pl.edu.pw.mini.taio.omino.core.Block;
import pl.edu.pw.mini.taio.omino.lib.generators.BlockGenerator;
import pl.edu.pw.mini.taio.omino.lib.generators.IncrementalBlockGenerator;
import pl.edu.pw.mini.taio.omino.lib.solvers.OptimalSquare;
import pl.edu.pw.mini.taio.omino.lib.solvers.Solver;

public class App {
    public static void main(String[] args) {
        BlockGenerator generator = new IncrementalBlockGenerator(5);
        Block[] blocks = generator.many().limit(9).toArray(Block[]::new);
        Solver solver = new OptimalSquare(blocks);
        Block[][] solution = solver.solve();
        for (Block[] arr : solution) {
            for (Block block : arr) {
                if(block == null) System.out.print("  ");
                else System.out.print(Integer.remainderUnsigned(block.hashCode(), 90) + 10);
            }
            System.out.println();
        }
    }
}
