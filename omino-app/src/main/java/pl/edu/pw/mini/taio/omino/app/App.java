package pl.edu.pw.mini.taio.omino.app;

import pl.edu.pw.mini.taio.omino.core.Block;
import pl.edu.pw.mini.taio.omino.lib.generators.BlockGenerator;
import pl.edu.pw.mini.taio.omino.lib.generators.IncrementalBlockGenerator;
import pl.edu.pw.mini.taio.omino.lib.solvers.OptimalRectangle;
import pl.edu.pw.mini.taio.omino.lib.solvers.RectangleSolver;

import java.awt.*;
import java.util.Arrays;
import java.util.Random;

public class App {
    public static void main(String[] args) {
        Random random = new Random();
        BlockGenerator generator = new IncrementalBlockGenerator(5);
        Block[] blocks = generator.many().limit(2).peek(b -> b.setColor(new Color(random.nextInt()))).toArray(Block[]::new);
        System.out.println(Arrays.toString(blocks));
        RectangleSolver solver = new OptimalRectangle(blocks);
        Block[][] solution = solver.benchmark();
        for (Block[] arr : solution) {
            for (Block block : arr) {
                if(block == null) System.out.print("  ");
                else System.out.print(Integer.remainderUnsigned(block.getColor().hashCode(), 90) + 10);
                System.out.print(' ');
            }
            System.out.println();
        }
        System.out.printf("Solution found in %.6fs, made %d cut%s%n", solver.getTime(), solver.getCuts(), solver.getCuts() != 1 ? "s" : "");
    }
}
