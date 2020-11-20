package pl.edu.pw.mini.taio.omino.app.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import pl.edu.pw.mini.taio.omino.app.controls.Canvas;
import pl.edu.pw.mini.taio.omino.app.utils.RandomColor;
import pl.edu.pw.mini.taio.omino.core.Block;
import pl.edu.pw.mini.taio.omino.lib.generators.IncrementalBlockGenerator;
import pl.edu.pw.mini.taio.omino.lib.solvers.FastRectangle;
import pl.edu.pw.mini.taio.omino.lib.solvers.RectangleSolver;

public class MainController {
    @FXML
    private Pane pane;

    private Canvas canvas;

    @FXML
    private void initialize() {
        canvas = new Canvas(pane);
        RandomColor random = new RandomColor();
        IncrementalBlockGenerator ibg = new IncrementalBlockGenerator(5);
        Block[] problem = ibg.many().limit(10).peek(block -> block.setColor(random.nextAwt())).toArray(Block[]::new);
        RectangleSolver solver = new FastRectangle(problem);
        Block[][] board = solver.solve();
        canvas.draw(board);
    }
}
