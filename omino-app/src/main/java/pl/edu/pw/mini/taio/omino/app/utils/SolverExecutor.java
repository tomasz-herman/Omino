package pl.edu.pw.mini.taio.omino.app.utils;

import pl.edu.pw.mini.taio.omino.core.Block;
import pl.edu.pw.mini.taio.omino.lib.solvers.Solver;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class SolverExecutor {
    private static final int TIMEOUT = 1000;
    private final Consumer<String> preparation;
    private Thread solverThread;

    public SolverExecutor(Consumer<String> preparation) {
        this.preparation = preparation;
    }

    public void execute(Solver solver, BiConsumer<Block[][], Double> solutionCallback) {
        finishLastTask();
        solverThread = new Thread(() -> {
            preparation.accept(solver.getClass().getSimpleName());
            Block[][] solution = solver.benchmark();
            if(solution != null) {
                solutionCallback.accept(solution, solver.getTime());
            }
        });
        solverThread.start();
    }

    public void finishLastTask() {
        if(solverThread != null) {
            solverThread.interrupt();
            try {
                solverThread.join(TIMEOUT);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
