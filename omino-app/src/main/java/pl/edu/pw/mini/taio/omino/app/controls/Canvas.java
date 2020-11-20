package pl.edu.pw.mini.taio.omino.app.controls;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import pl.edu.pw.mini.taio.omino.core.Block;

import static pl.edu.pw.mini.taio.omino.app.utils.ColorConverter.awtToFx;

public class Canvas {
    private final static int MARGIN = 25;
    private final static int BLOCK_SIZE = 40;

    private Pane pane;

    public Canvas(Pane pane) {
        this.pane = pane;
    }

    public void draw(Block[][] board) {
        int width = board[0].length * BLOCK_SIZE+ 2 * MARGIN;
        int height = board.length * BLOCK_SIZE + 2 * MARGIN;
        pane.setPrefSize(width, height);
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                Block block = board[i][j];
                Rectangle rectangle = new Rectangle(MARGIN + j * BLOCK_SIZE, MARGIN + i * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                rectangle.setFill(awtToFx(block.getColor()));
                rectangle.setStroke(awtToFx(block.getColor()).invert());
                pane.getChildren().add(rectangle);
            }
        }
    }

    public void draw(Block[] list) {

    }

    public void clear() {
        pane.getChildren().clear();
    }
}
