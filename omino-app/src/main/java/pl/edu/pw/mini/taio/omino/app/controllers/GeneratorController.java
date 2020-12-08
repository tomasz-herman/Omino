package pl.edu.pw.mini.taio.omino.app.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;
import pl.edu.pw.mini.taio.omino.app.utils.RandomColor;
import pl.edu.pw.mini.taio.omino.app.utils.StringIntegerConverter;
import pl.edu.pw.mini.taio.omino.core.Block;
import pl.edu.pw.mini.taio.omino.lib.generators.RandomBlockGenerator;
import pl.edu.pw.mini.taio.omino.lib.generators.VaryingSizeBlockGenerator;

import java.util.function.Consumer;

public class GeneratorController {
    private static final int MIN_BLOCK_SIZE = 1;
    private static final int MAX_BLOCK_SIZE = 1000;
    private static final int MIN_BLOCKS = 1;
    private static final int MAX_BLOCKS = 1000;

    private RandomColor random;
    private Consumer<Block[]> callback;
    private Stage stage;

    @FXML private Spinner<Integer> minimumBlockSizeSpinner;
    @FXML private Spinner<Integer> maximumBlockSizeSpinner;
    @FXML private Spinner<Integer> blockCountSpinner;

    @FXML private void initialize() {
        random = new RandomColor();
        setupSpinner(minimumBlockSizeSpinner, MIN_BLOCK_SIZE, MAX_BLOCK_SIZE, 5);
        setupSpinner(maximumBlockSizeSpinner, MIN_BLOCK_SIZE, MAX_BLOCK_SIZE, 5);
        setupSpinner(blockCountSpinner, MIN_BLOCKS, MAX_BLOCKS, 5);

        minimumBlockSizeSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue > maximumBlockSizeSpinner.getValue()) {
                setupSpinner(maximumBlockSizeSpinner, MIN_BLOCK_SIZE, MAX_BLOCK_SIZE, newValue);
            }
        });

        maximumBlockSizeSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue < minimumBlockSizeSpinner.getValue()) {
                setupSpinner(minimumBlockSizeSpinner, MIN_BLOCK_SIZE, MAX_BLOCK_SIZE, newValue);
            }
        });
    }

    private void setupSpinner(Spinner<Integer> spinner, int min, int max, int val) {
        spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(min, max, val));
        spinner.getValueFactory().setConverter(new StringIntegerConverter());
    }

    public void setupStage(Stage stage) {
        this.stage = stage;
    }

    public void setupCallback(Consumer<Block[]> callback) {
        this.callback = callback;
    }

    @FXML private void onCancel(ActionEvent event) {
        stage.close();
    }

    @FXML private void onGenerate(ActionEvent event) {
        int min = minimumBlockSizeSpinner.getValue();
        int max = maximumBlockSizeSpinner.getValue();
        int count = blockCountSpinner.getValue();
        VaryingSizeBlockGenerator generator = new VaryingSizeBlockGenerator(min, max, RandomBlockGenerator::new);
        callback.accept(generator.many().limit(count).peek(block -> block.setColor(random.nextAwt())).toArray(Block[]::new));
        stage.close();
    }
}
