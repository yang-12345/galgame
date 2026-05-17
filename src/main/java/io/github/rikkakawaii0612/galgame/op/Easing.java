package io.github.rikkakawaii0612.galgame.op;

import java.util.function.DoubleFunction;

public class Easing {
    public static final DoubleFunction<Double> linear = t -> t;

    public static final DoubleFunction<Double> easeInQuad = t -> t * t;
    public static final DoubleFunction<Double> easeOutQuad = t -> t * (2 - t);
    public static final DoubleFunction<Double> easeInOutQuad = t -> t < 0.5 ?
            2 * t * t : -1 + (4 - 2 * t) * t;

    public static final DoubleFunction<Double> easeInCubic = t -> t * t * t;
    public static final DoubleFunction<Double> easeOutCubic = t -> (--t) * t * t + 1;
    public static final DoubleFunction<Double> easeInOutCubic = t -> t < 0.5 ?
            4 * t * t * t : (t - 1) * (2 * t - 2) * (2 * t - 2) + 1;

    // 弹跳缓出（简单版）
    public static final DoubleFunction<Double> easeOutBounce = t -> {
        if (t < 1 / 2.75) {
            return 7.5625 * t * t;
        } else if (t < 2 / 2.75) {
            t -= 1.5 / 2.75;
            return 7.5625 * t * t + 0.75;
        } else if (t < 2.5 / 2.75) {
            t -= 2.25 / 2.75;
            return 7.5625 * t * t + 0.9375;
        } else {
            t -= 2.625 / 2.75;
            return 7.5625 * t * t + 0.984375;
        }
    };
}