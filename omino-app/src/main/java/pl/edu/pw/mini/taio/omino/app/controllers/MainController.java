package pl.edu.pw.mini.taio.omino.app.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pl.edu.pw.mini.taio.omino.app.controls.Canvas;
import pl.edu.pw.mini.taio.omino.app.utils.OminoBoard;
import pl.edu.pw.mini.taio.omino.app.utils.SolverExecutor;
import pl.edu.pw.mini.taio.omino.core.Block;
import pl.edu.pw.mini.taio.omino.lib.solvers.*;

import java.io.IOException;

public class MainController {

    @FXML private Label infoLabel;
    @FXML private ProgressIndicator progressIndicator;
    @FXML private Label statusLabel;
    @FXML private Pane pane;

    private Canvas canvas;
    private OminoBoard board;
    private SolverExecutor executor;

    @FXML private void initialize() {
        canvas = new Canvas(pane);
        board = new OminoBoard(canvas);
        executor = new SolverExecutor(this::prepare);
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
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/generator.fxml"));
            Parent root = loader.load();
            GeneratorController controller = loader.getController();
            controller.setupCallback(this::setGeneratedBlocks);
            controller.setupStage(stage);
            stage.setScene(new Scene(root));
            stage.setTitle("Generate blocks");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML private void onAdd(ActionEvent event) {

    }

    @FXML private void onClear(ActionEvent event) {
        setGeneratedBlocks();
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

    private void displaySolution(Block[][] board, String method, double time, Integer cuts) {
        Platform.runLater(() -> {
            canvas.clear();
            canvas.draw(board);
            infoLabel.setText(String.format("%s: %.3fs%s", method, time, cuts == null ? "" : String.format(", %d cuts", cuts)));
            progressIndicator.setVisible(false);
            statusLabel.setText("Idle");
        });
    }

    private void setGeneratedBlocks(Block... blocks) {
        executor.finishLastTask();
        progressIndicator.setVisible(false);
        statusLabel.setText("Idle");
        infoLabel.setText("");
        board.clear();
        canvas.clear();
        board.add(blocks);
        canvas.draw(blocks);
    }
}
