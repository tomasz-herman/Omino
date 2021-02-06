package pl.edu.pw.mini.taio.omino.app.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import pl.edu.pw.mini.taio.omino.app.controls.Canvas;
import pl.edu.pw.mini.taio.omino.core.Block;
import pl.edu.pw.mini.taio.omino.core.Pixel;

import java.awt.*;
import java.util.stream.Stream;

public class AdderController {
    private final static int SIZE = 10;
    private final static Block PIXEL = new Block(Stream.of(new Pixel(0, 0)), new Color(0xf1886da));

    @FXML private Pane pane;

    private Canvas canvas;
    private final Block[][] board = new Block[SIZE][SIZE];
    private Stage stage;

    @FXML private void initialize() {
        canvas = new Canvas(pane);
        canvas.draw(board);
    }

    public void setupStage(Stage stage) {
        this.stage = stage;
    }

    @FXML private void onCancel(ActionEvent event) {
        stage.close();
    }

    @FXML private void onAdd(ActionEvent event) {

    }

    @FXML private void onClick(MouseEvent event) {
        Integer x, y;
        x = getIndex(event.getX());
        y = getIndex(event.getY());
        if (x == null || y == null) return;
        if (board[y][x] == null){
            board[y][x] = PIXEL;
        } else {
            board[y][x] = null;
        }
        canvas.clear();
        canvas.draw(board);
    }

    private Integer getIndex(double click) {
        int dist = Canvas.MARGIN;
        for (int i = 0; i < SIZE; i++) {
            dist += Canvas.SPACING;
            if(click >= dist && click <= dist + Canvas.BLOCK_SIZE) return i;
            dist += Canvas.BLOCK_SIZE;
        }
        return null;
    }
}
