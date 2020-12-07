package pl.edu.pw.mini.taio.omino.lib.solvers;

import pl.edu.pw.mini.taio.omino.core.Block;
import pl.edu.pw.mini.taio.omino.core.Pixel;

public abstract class Solver {
    private static final int NANO = 1_000_000_000;

    protected final Block[] blocks;
    private double time;

    public Solver(Block[] blocks) {
        this.blocks = blocks;
    }

    public abstract Block[][] solve();

    public final Block[][] benchmark() {
        long start = System.nanoTime();
        Block[][] solution = solve();
        long end = System.nanoTime();
        time = (double) (end - start) / NANO;
        return solution;
    }

    public final double getTime() {
        return time;
    }

    protected static void insert(Block[][] board, Block block, int i, int j) {
        for (Pixel pixel : block.getPixels()) {
            board[i + pixel.getX()][j + pixel.getY()] = block;
        }
    }

    protected static void erase(Block[][] board, Block block, int i, int j) {
        for (Pixel pixel : block.getPixels()) {
            board[i + pixel.getX()][j + pixel.getY()] = null;
        }
    }

    protected static boolean available(Block[][] board, Block block, int i, int j) {
        for (Pixel pixel : block.getPixels()) {
            if(board[i + pixel.getX()][j + pixel.getY()] != null) return false;
        }
        return true;
    }

}
