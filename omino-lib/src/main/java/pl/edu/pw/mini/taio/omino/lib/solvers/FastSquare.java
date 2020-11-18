package pl.edu.pw.mini.taio.omino.lib.solvers;

import pl.edu.pw.mini.taio.omino.core.Block;

import java.util.Arrays;

public class FastSquare extends Solver {

    public FastSquare(Block[] blocks) {
        super(blocks);
    }

    @Override
    public Block[][] solve() {
        if (blocks == null || blocks.length == 0)
            return new Block[0][];
        int pixels = Arrays.stream(blocks).mapToInt(Block::getSize).sum();
        int min = (int) Math.ceil(Math.sqrt(pixels));
        next_board:
        for (int size = min; size < pixels; size++) {
            Block[][] solution = new Block[size][size];
            next_block:
            for (Block block : blocks) {
                for (Block rotation : block.getRotations()) {
                    for (int i = 0; i < solution.length - rotation.getWidth() + 1; i++) {
                        for (int j = 0; j < solution.length - rotation.getHeight() + 1; j++) {
                            if (available(solution, rotation, i, j)) {
                                insert(solution, rotation, i, j);
                                continue next_block;
                            }
                        }
                    }
                }
                continue next_board;
            }
            return solution;
        }
        return null;
    }
}
