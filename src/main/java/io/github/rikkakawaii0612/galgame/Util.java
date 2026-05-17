package io.github.rikkakawaii0612.galgame;

import javafx.animation.Interpolator;

import java.io.InputStream;

public class Util {
    public static final Interpolator CUBE_IN = new Interpolator() {
        @Override
        protected double curve(double v) {
            v = 1.0D - v;
            return 1.0D - v * v * v;
        }
    };

    public static InputStream resource(String path) {
        return Util.class.getResourceAsStream(path);
    }
}
