package pl.edu.pw.mini.taio.omino.app.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.Pane;
import pl.edu.pw.mini.taio.omino.app.controls.Canvas;
import pl.edu.pw.mini.taio.omino.app.utils.OminoBoard;
import pl.edu.pw.mini.taio.omino.app.utils.RandomColor;
import pl.edu.pw.mini.taio.omino.app.utils.SolverExecutor;
import pl.edu.pw.mini.taio.omino.core.Block;
import pl.edu.pw.mini.taio.omino.lib.generators.IncrementalBlockGenerator;
import pl.edu.pw.mini.taio.omino.lib.solvers.*;

public class MainController {

    @FXML private Label infoLabel;
    @FXML private ProgressIndicator progressIndicator;
    @FXML private Label statusLabel;
    @FXML private Pane pane;

    private Canvas canvas;
    private OminoBoard board;
    private SolverExecutor executor;
    private RandomColor random;


    @FXML private void initialize() {
        canvas = new Canvas(pane);
        board = new OminoBoard(canvas);
        executor = new SolverExecutor(this::prepare);
        random = new RandomColor();
        progressIndicator.setVisible(false);
    }

    @FXML private void onSolveOptimalSquare(ActionEvent event) {
        executor.execute(new OptimalSquare(board.getList()), this::displaySolution);
    }

    @FXML private void onSolveFastSquare(ActionEvent event) {
        executor.execute(new FastSquare(board.getList()), this::displaySolution);
    }

    @FXML private void onSolveOptimalRectangle(ActionEvent event) {
        executor.execute(new OptimalRectangle(board.getList()), this::displaySolution);
    }

    @FXML private void onSolveFastRectangle(ActionEvent event) {
        executor.execute(new FastRectangle(board.getList()), this::displaySolution);
    }

    @FXML private void onGenerate(ActionEvent event) {
        IncrementalBlockGenerator ibg = new IncrementalBlockGenerator(5);
        Block[] problem = ibg.many().limit(15).peek(block -> block.setColor(random.nextAwt())).toArray(Block[]::new);
        board.add(problem);
    }

    @FXML private void onAdd(ActionEvent event) {

    }

    @FXML private void onClear(ActionEvent event) {

    }

    @FXML private void onAbout(ActionEvent event) {

    }

    @FXML private void onClose(ActionEvent event) {
        Platform.exit();
    }

    private void prepare(String method) {
        Platform.runLater(() -> {
            infoLabel.setText("");
            progressIndicator.setVisible(true);
            statusLabel.setText(method);
        });
    }

    private void displaySolution(Block[][] board, double time) {
        Platform.runLater(() -> {
            canvas.clear();
            canvas.draw(board);
            infoLabel.setText(String.format("%.3f s", time));
            progressIndicator.setVisible(false);
            statusLabel.setText("Idle");
        });
    }
}
