package pl.edu.pw.mini.taio.omino.lib.solvers;

import pl.edu.pw.mini.taio.omino.core.Block;

import java.awt.Dimension;
import java.util.Arrays;

public abstract class RectangleSolver extends Solver {

    protected int cuts = -1;

    public RectangleSolver(Block[] blocks) {
        super(blocks);
    }

    public int getCuts() {
        return cuts;
    }

    protected Block[][] prepareBoard() {
        if (blocks == null || blocks.length == 0) return new Block[0][0];
        int area = Arrays.stream(blocks).mapToInt(Block::getSize).sum();
        if(area == 0) return new Block[0][0];
        Dimension dimension = getDimensions(area);
        if(dimension == null) return null;
        return new Block[dimension.height][dimension.width];
    }

    protected static Dimension getDimensions(int area) {
        if(area <= 0) throw new IllegalArgumentException("Area must be a positive integer!");
        for (int i = (int) Math.sqrt(area); i > 0; i--) {
            if (area / i * i == area) {
                return new Dimension(area / i, i);
            }
        }
        return null;
    }
}
