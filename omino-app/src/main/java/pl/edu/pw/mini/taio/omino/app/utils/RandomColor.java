package pl.edu.pw.mini.taio.omino.app.utils;

import java.util.Random;

import static pl.edu.pw.mini.taio.omino.app.utils.ColorConverter.fxToAwt;

public class RandomColor {

    private final Random random;

    public RandomColor() {
        random = new Random();
    }

    public RandomColor(long seed) {
        random = new Random(seed);
    }

    public javafx.scene.paint.Color nextFx() {
        return javafx.scene.paint.Color.hsb(
                random.nextDouble() * 360,
                1 - random.nextDouble() * random.nextDouble(),
                1 - random.nextDouble() * random.nextDouble());
    }

    public java.awt.Color nextAwt() {
        return fxToAwt(nextFx());
    }
}
