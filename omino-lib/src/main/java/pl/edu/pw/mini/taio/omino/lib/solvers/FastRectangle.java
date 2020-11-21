package pl.edu.pw.mini.taio.omino.lib.solvers;

import pl.edu.pw.mini.taio.omino.core.Block;

import java.awt.Dimension;
import java.util.*;
import java.util.stream.Collectors;

public class FastRectangle extends RectangleSolver {

    public FastRectangle(Block[] blocks) {
        super(blocks);
    }

    @Override
    public Block[][] solve() {
        cuts = 0;
        Block[][] solution = prepareBoard();
        if(solution == null || solution.length == 0) return solution;
        Deque<Block> queue = Arrays.stream(blocks).collect(Collectors.toCollection(LinkedList::new));
        next_block:
        while(!queue.isEmpty()) {
            Block block = queue.pollFirst();
            for (Block rotation : block.getRotations()) {
                for (int i = 0; i < solution.length - rotation.getWidth() + 1; i++) {
                    for (int j = 0; j < solution[0].length - rotation.getHeight() + 1; j++) {
                        if(Thread.interrupted()) return null;
                        if (!available(solution, rotation, i, j)) continue;
                        insert(solution, rotation, i, j);
                        continue next_block;
                    }
                }
            }
            int cuts = 1;
            while(true) {
                var cutBlocks = block.getCutBlocks(cuts);
                if(cutBlocks == null) {
                    queue.addLast(block);
                    continue next_block;
                }
                for (Collection<Block> blockCollection : cutBlocks) {
                    if(blockCollection.size() > 1) {
                        for (Block b : blockCollection) {
                            if(block.getSize() <= 2) queue.addLast(b);
                            else queue.addFirst(b);
                        }
                        this.cuts += cuts;
                        continue next_block;
                    }
                }
                cuts++;
            }
        }
        return solution;
    }
}
