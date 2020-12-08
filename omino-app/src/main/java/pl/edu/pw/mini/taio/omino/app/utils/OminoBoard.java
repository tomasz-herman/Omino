package pl.edu.pw.mini.taio.omino.app.utils;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import pl.edu.pw.mini.taio.omino.app.controls.Canvas;
import pl.edu.pw.mini.taio.omino.core.Block;

public class OminoBoard {
    private final ObservableList<Block> list;

    public OminoBoard(Canvas canvas) {
        list = FXCollections.observableArrayList();
        list.addListener((ListChangeListener<Block>) change -> canvas.draw(getList()));
    }

    public void add(Block... blocks) {
        list.addAll(blocks);
    }

    public void clear() {
        list.clear();
    }

    public Block[] getList() {
        return list.toArray(new Block[0]);
    }
}
