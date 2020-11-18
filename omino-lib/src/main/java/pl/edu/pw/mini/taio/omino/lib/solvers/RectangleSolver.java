package pl.edu.pw.mini.taio.omino.lib.solvers;

import pl.edu.pw.mini.taio.omino.core.Block;

import java.awt.Dimension;

public abstract class RectangleSolver extends Solver {

    protected int cuts = -1;

    public RectangleSolver(Block[] blocks) {
        super(blocks);
    }

    public int getCuts() {
        return cuts;
    }

    protected static Dimension getDimensions(int area) {
        if(area <= 0) throw new IllegalArgumentException("Area must be a positive integer!");
        for (int i = (int) Math.sqrt(area); i > 0; i--) {
            if (area / i * i == area) {
                return new Dimension(i, area / i);
            }
        }
        return null;
    }
}
