package pl.edu.pw.mini.taio.omino.lib.solvers;

import pl.edu.pw.mini.taio.omino.core.Block;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class OptimalRectangle extends RectangleSolver {

    public OptimalRectangle(Block[] blocks) {
        super(blocks);
    }

    @Override
    public Block[][] solve() {
        cuts = 0;
        Block[][] solution = prepareBoard();
        if(solution == null || solution.length == 0) return solution;
        while (true) {
            int[] cutsDistribution = new int[blocks.length];
            if (solveRec(cutsDistribution, 0, cuts, solution)) return solution;
            if(Thread.interrupted()) return null;
            cuts++;
        }
    }

    private boolean solveRec(int[] cutsDistribution, int level, int cuts, Block[][] solution) {
        if (cuts == 0) {
            return solveRecRec(new ArrayList<>(), 0, cutsDistribution, solution);
        }

        if (level >= cutsDistribution.length) return false;

        for (int i = 0; i <= cuts; i++) {
            cutsDistribution[level] += i;
            if (solveRec(cutsDistribution, level + 1, cuts - i, solution)) return true;
            cutsDistribution[level] -= i;
        }

        return false;
    }

    private boolean solveRecRec(List<Block> blockList, int level, int[] cutsDistribution, Block[][] solution) {
        if (level == cutsDistribution.length) {
            return solveRecRecRec(solution, blockList, 0);
        }

        var combinations = blocks[level].getCutBlocks(cutsDistribution[level]);
        if (combinations == null) return false;

        for (Collection<Block> combination : combinations) {
            blockList.addAll(combination);
            if (solveRecRec(blockList, level + 1, cutsDistribution, solution)) return true;
            blockList.subList(blockList.size()-combination.size(), blockList.size()).clear();
        }

        return false;
    }

    private boolean solveRecRecRec(Block[][] solution, List<Block> blockList, int level) {
        if (level == blockList.size()) {
            return true;
        }

        for (Block rotation : blockList.get(level).getRotations()) {
            for (int i = 0; i < solution.length - rotation.getWidth() + 1; i++) {
                for (int j = 0; j < solution[0].length - rotation.getHeight() + 1; j++) {
                    if(Thread.currentThread().isInterrupted()) return false;
                    if (!available(solution, rotation, i, j)) continue;
                    insert(solution, rotation, i, j);
                    if (solveRecRecRec(solution, blockList, level + 1)) return true;
                    erase(solution, rotation, i, j);
                }
            }
        }

        return false;
    }
}
