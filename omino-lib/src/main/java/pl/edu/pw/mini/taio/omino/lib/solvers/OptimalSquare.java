package pl.edu.pw.mini.taio.omino.lib.solvers;

import pl.edu.pw.mini.taio.omino.core.Block;

import java.util.Arrays;

public class OptimalSquare extends Solver {

    public OptimalSquare(Block[] blocks) {
        super(blocks);
    }

    @Override
    public Block[][] solve() {
        if (blocks == null || blocks.length == 0)
            return new Block[0][];
        int pixels = Arrays.stream(blocks).mapToInt(Block::getSize).sum();
        int min = (int) Math.ceil(Math.sqrt(pixels));
        System.out.println(min + ", " + pixels);
        for (int i = min; i < pixels; i++) {
            Block[][] solution = new Block[i][i];
            if(recursion(solution, 0)) return solution;
        }
        return null;
    }

    public boolean recursion(Block[][] solution, int level) {
        if(blocks.length == level) return true;
        for (Block rotation : blocks[level].getRotations()) {
            for (int i = 0; i < solution.length - rotation.getWidth() + 1; i++) {
                for (int j = 0; j < solution.length - rotation.getHeight() + 1; j++) {
                    if (!available(solution, rotation, i, j)) continue;
                    insert(solution, rotation, i, j);
                    if (recursion(solution, level + 1)) return true;
                    erase(solution, rotation, i, j);
                }
            }
        }
        return false;
    }

}
