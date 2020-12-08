package pl.edu.pw.mini.taio.omino.app.utils;

import javafx.util.StringConverter;

public class StringIntegerConverter extends StringConverter<Integer> {
    @Override
    public String toString(Integer object) {
        return Integer.toString(object);
    }

    @Override
    public Integer fromString(String string) {
        try {
            return Integer.parseInt(string);
        } catch (NumberFormatException e) {
            return 1;
        }
    }
}
