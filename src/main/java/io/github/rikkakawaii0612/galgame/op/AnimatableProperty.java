package io.github.rikkakawaii0612.galgame.op;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleFunction;

public class AnimatableProperty {
    private double baseValue;                     // 默认值（无关键帧覆盖时使用）

    // 关键帧定义
    private static class Keyframe {
        double startTime, endTime;
        double startValue, endValue;
        DoubleFunction<Double> easing;

        Keyframe(double sTime, double eTime, double sVal, double eVal, DoubleFunction<Double> e) {
            this.startTime = sTime;
            this.endTime = eTime;
            this.startValue = sVal;
            this.endValue = eVal;
            this.easing = e;
        }
    }

    private final List<Keyframe> keyframes = new ArrayList<>();

    public AnimatableProperty(double initialValue) {
        this.baseValue = initialValue;
    }

    /**
     * 添加一个关键帧动画。
     * 起始值自动取前一个关键帧的结束值（若无则取当前 baseValue），保证动画连续。
     */
    public void addKeyframe(double startTime, double endTime, double targetValue,
                            DoubleFunction<Double> easing) {
        double startVal = keyframes.isEmpty() ? baseValue : keyframes.getLast().endValue;
        keyframes.add(new Keyframe(startTime, endTime, startVal, targetValue, easing));
    }

    public void addKeyframe(double startTime, double endTime, double startValue, double targetValue,
                            DoubleFunction<Double> easing) {
        keyframes.add(new Keyframe(startTime, endTime, startValue, targetValue, easing));
    }

    public void addKeyframe(double time, double value) {
        keyframes.add(new Keyframe(time, time, value, value, Easing.linear));
    }

    /**
     * 根据全局时间计算当前值。
     */
    public double getValueAt(double globalTime) {
        // 查找第一个覆盖 globalTime 的关键帧
        for (Keyframe kf : keyframes) {
            if (globalTime >= kf.startTime && globalTime < kf.endTime) {
                double duration = kf.endTime - kf.startTime;
                double t = (globalTime - kf.startTime) / duration;
                double factor = kf.easing.apply(t);
                return kf.startValue + (kf.endValue - kf.startValue) * factor;
            }
        }

        // 没有活跃关键帧：返回最近一个关键帧的结束值，或 baseValue
        Keyframe keyframe = null;
        if (!keyframes.isEmpty()) {
            for (Keyframe kf : keyframes) {
                if ((keyframe == null || keyframe.endTime <= kf.endTime) && globalTime >= kf.startTime) {
                    keyframe = kf;
                }
            }
        }
        if (keyframe != null) {
            return keyframe.endValue;
        }
        return baseValue;
    }

    // 为了方便，保留一个立即获取当前值的方法（外部无需知道内部结构）
    public double get() {
        // 外部需要配合全局时间使用，这里仅作占位，实际由 RenderNode 调用 getValueAt(globalTime)
        return baseValue;
    }
}