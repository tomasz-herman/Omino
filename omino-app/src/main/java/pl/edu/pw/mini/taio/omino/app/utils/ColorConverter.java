package pl.edu.pw.mini.taio.omino.app.utils;

public class ColorConverter {
    public static javafx.scene.paint.Color awtToFx(java.awt.Color color) {
        return javafx.scene.paint.Color.rgb(color.getRed(), color.getGreen(), color.getBlue());
    }

    public static java.awt.Color fxToAwt(javafx.scene.paint.Color color) {
        return new java.awt.Color((float)color.getRed(), (float)color.getGreen(), (float)color.getBlue());
    }
}
