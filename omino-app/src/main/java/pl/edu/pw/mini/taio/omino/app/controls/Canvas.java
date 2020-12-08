package pl.edu.pw.mini.taio.omino.app.controls;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import pl.edu.pw.mini.taio.omino.core.Block;
import pl.edu.pw.mini.taio.omino.lib.solvers.FastSquare;
import pl.edu.pw.mini.taio.omino.lib.solvers.Solver;

import static pl.edu.pw.mini.taio.omino.app.utils.ColorConverter.awtToFx;

public class Canvas {
    private final static int MARGIN = 25;
    private final static int BLOCK_SIZE = 40;
    private final static int SPACING = 2;

    private final Pane pane;

    public Canvas(Pane pane) {
        this.pane = pane;
    }

    public void draw(Block[][] board) {
        int h = board.length;
        int w = h == 0 ? 0 : board[0].length;
        int width = w * (BLOCK_SIZE + SPACING) + 2 * MARGIN;
        int height = h * (BLOCK_SIZE + SPACING) + 2 * MARGIN;
        pane.setPrefSize(width, height);

        Rectangle border = new Rectangle(
                MARGIN - SPACING * 0.5,
                MARGIN - SPACING * 0.5,
                w * (BLOCK_SIZE + SPACING),
                h * (BLOCK_SIZE + SPACING));
        border.setFill(Color.TRANSPARENT);
        border.setStroke(Color.BLACK);
        border.setStrokeWidth(SPACING);
        pane.getChildren().add(border);

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                Block block = board[i][j];
                if(block == null) continue;
                Rectangle rectangle = new Rectangle(MARGIN + j * (BLOCK_SIZE + SPACING), MARGIN + i * (BLOCK_SIZE + SPACING), BLOCK_SIZE, BLOCK_SIZE);
                rectangle.setFill(awtToFx(block.getColor()));
                pane.getChildren().add(rectangle);
            }
        }
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                Block block = board[i][j];
                Block lower = i + 1 < board.length ? board[i + 1][j] : null;
                Block right = j + 1 < board[0].length ? board[i][j + 1] : null;

                Line horizontal = new Line(
                        MARGIN + j * (BLOCK_SIZE + SPACING) + 0.5 * SPACING,
                        MARGIN + (i + 1) * (BLOCK_SIZE + SPACING) - 0.5 * SPACING,
                        MARGIN + (j + 1) * (BLOCK_SIZE + SPACING) - 1.5 * SPACING,
                        MARGIN + (i + 1) * (BLOCK_SIZE + SPACING) - 0.5 * SPACING);
                drawLine(block, lower, horizontal);

                Line vertical = new Line(
                        MARGIN + (j + 1) * (BLOCK_SIZE + SPACING) - 0.5 * SPACING,
                        MARGIN + i * (BLOCK_SIZE + SPACING),
                        MARGIN + (j + 1) * (BLOCK_SIZE + SPACING) - 0.5 * SPACING,
                        MARGIN + (i + 1) * (BLOCK_SIZE + SPACING) - SPACING);
                drawLine(block, right, vertical);
            }
        }
    }

    private void drawLine(Block block, Block neighbour, Line line) {
        line.setStrokeWidth(SPACING);
        if(block != null && block == neighbour) {
            Color color = awtToFx(block.getColor());
            if(color.getBrightness() > 0.5) {
                color = color.darker();
            } else {
                color = color.brighter();
            }
            if(color.getSaturation() > 0.5) {
                color = color.desaturate();
            } else {
                color = color.saturate();
            }
            line.setStroke(color);
        } else {
            line.setStroke(Color.BLACK);
        }
        pane.getChildren().add(line);
    }

    public void draw(Block[] list) {
        Solver solver = new FastSquare(list);
        Block[][] board = solver.solve();
        draw(board);
    }

    public void clear() {
        pane.getChildren().clear();
    }
}
