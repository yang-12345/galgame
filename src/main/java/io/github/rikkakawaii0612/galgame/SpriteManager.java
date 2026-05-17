package io.github.rikkakawaii0612.galgame;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpriteManager {
    private final Pane layer;
    private final Map<String, ImageView> sprites;    // 记录每个立绘当前运行的动画，以便停止
    private final Map<String, Animation> activeAnimations;

    public SpriteManager() {
        layer = new Pane();
        layer.setPrefSize(1280, 720);
        sprites = new HashMap<>();
        activeAnimations = new HashMap<>();
    }

    public Pane getLayer() {
        return layer;
    }


    public void setSprite(String id, String imagePath) {
        ImageView iv = sprites.get(id);
        if (iv == null) {
            iv = new ImageView();
            iv.setPreserveRatio(true);
            layer.getChildren().add(iv);
            sprites.put(id, iv);
        }
        Image img = new Image(getClass().getResourceAsStream(imagePath));
        iv.setImage(img);
        iv.setOpacity(1);
    }

    /**
     * 添加或更新一个立绘
     * @param id 唯一标识
     * @param imagePath 图片路径
     * @param x 水平位置（像素）
     * @param y 垂直位置（默认底部对齐可用 layer高度 - 图片高度）
     * @param scale 缩放比例
     * @param fadeIn 是否淡入
     */
    public void setSprite(String id, String imagePath, double x, double y, double scale, boolean fadeIn) {
        ImageView iv = sprites.get(id);
        if (iv == null) {
            iv = new ImageView();
            iv.setPreserveRatio(true);
            layer.getChildren().add(iv);
            sprites.put(id, iv);
        }
        Image img = new Image(getClass().getResourceAsStream(imagePath));
        iv.setImage(img);
        iv.setScaleX(scale);
        iv.setScaleY(scale);
        // 计算实际高度用于定位
        iv.setLayoutX(x - img.getWidth() / 2);
        iv.setLayoutY(y - img.getHeight() / 2);
        if (fadeIn) {
            iv.setOpacity(0);
            ImageView finalIv = iv;
            Platform.runLater(() -> {
                FadeTransition ft = new FadeTransition(Duration.seconds(0.25D), finalIv);
                ft.setToValue(1);
                ft.play();
            });
        } else {
            iv.setOpacity(1);
        }
    }

    /**
     * 移除立绘（支持淡出）
     */
    public void removeSprite(String id, boolean fadeOut) {
        ImageView iv = sprites.get(id);
        if (iv == null) return;
        stopAnimation(id); // 停止所有动画
        if (fadeOut) {
            FadeTransition ft = new FadeTransition(Duration.seconds(0.5), iv);
            ft.setToValue(0);
            ft.setOnFinished(e -> {
                layer.getChildren().remove(iv);
                sprites.remove(id);
                activeAnimations.remove(id);
            });
            ft.play();
        } else {
            layer.getChildren().remove(iv);
            sprites.remove(id);
            activeAnimations.remove(id);
        }
    }

    public void clearAll() {
        for (String id : sprites.keySet()) {
            stopAnimation(id);
        }
        layer.getChildren().clear();
        sprites.clear();
        activeAnimations.clear();
    }

    // ------------------------------------------
    //  动画相关
    // ------------------------------------------

    /**
     * 停止指定立绘的所有动画（淡入淡出除外）
     */
    public void stopAnimation(String id) {
        Animation anim = activeAnimations.remove(id);
        if (anim != null) {
            anim.stop();
        }
    }

    /**
     * 执行一个简单的单步动画（位移/旋转/缩放，三选一）
     */
    public void animateSprite(String id, AnimationStep step) {
        animateSprite(id, step, null);
    }

    /**
     * 执行一个简单的动画，完成后执行回调
     */
    public void animateSprite(String id, AnimationStep step, Runnable onFinished) {
        ImageView iv = sprites.get(id);
        if (iv == null) return;
        stopAnimation(id);

        Animation anim = createAnimation(iv, step);
        if (onFinished != null) {
            anim.setOnFinished(e -> onFinished.run());
        }
        activeAnimations.put(id, anim);
        anim.play();
    }

    /**
     * 顺序执行多个动画步骤（一个接一个）
     */
    public void animateSpriteSequence(String id, List<AnimationStep> steps) {
        animateSpriteSequence(id, steps, null);
    }

    /**
     * 顺序执行多个动画步骤，全部完成后回调
     */
    public void animateSpriteSequence(String id, List<AnimationStep> steps, Runnable onFinished) {
        ImageView iv = sprites.get(id);
        if (iv == null || steps.isEmpty()) return;
        stopAnimation(id);

        SequentialTransition seq = new SequentialTransition();
        for (AnimationStep step : steps) {
            seq.getChildren().add(createAnimation(iv, step));
        }
        if (onFinished != null) {
            seq.setOnFinished(e -> onFinished.run());
        }
        activeAnimations.put(id, seq);
        seq.play();
    }

    /**
     * 并行执行多个动画步骤（同时进行）
     */
    public void animateSpriteParallel(String id, List<AnimationStep> steps) {
        animateSpriteParallel(id, steps, null);
    }

    /**
     * 并行执行多个动画步骤，全部完成后回调
     */
    public void animateSpriteParallel(String id, List<AnimationStep> steps, Runnable onFinished) {
        ImageView iv = sprites.get(id);
        if (iv == null || steps.isEmpty()) return;
        stopAnimation(id);

        ParallelTransition par = new ParallelTransition();
        for (AnimationStep step : steps) {
            par.getChildren().add(createAnimation(iv, step));
        }
        if (onFinished != null) {
            par.setOnFinished(e -> onFinished.run());
        }
        activeAnimations.put(id, par);
        par.play();
    }

    /**
     * 组合动画：一个 Parallel 作为一步，再与其他步 Sequential 拼接
     * 该方法会停止之前的动画并执行新序列
     */
    public void animateSpriteCustom(String id, Animation customAnimation) {
        ImageView iv = sprites.get(id);
        if (iv == null) return;
        stopAnimation(id);
        // 确保动画目标是当前 ImageView（如果传入的自定义动画未绑定目标，需内部替换，此处约定 customAnimation 已绑定）
        activeAnimations.put(id, customAnimation);
        customAnimation.play();
    }

    // -------- 内部工具方法 --------

    private ImageView getOrCreateImageView(String id) {
        ImageView iv = sprites.get(id);
        if (iv == null) {
            iv = new ImageView();
            iv.setPreserveRatio(true);
            layer.getChildren().add(iv);
            sprites.put(id, iv);
        }
        return iv;
    }

    /**
     * 根据 AnimationStep 创建一个针对 ImageView 的 Animation 子类
     */
    private Animation createAnimation(ImageView iv, AnimationStep step) {
        Duration dur = Duration.millis(step.durationMs);
        Interpolator interpolator = step.interpolator != null ? step.interpolator : Interpolator.EASE_BOTH;

        Animation anim;
        switch (step.type) {
            case TRANSLATE -> {
                TranslateTransition tt = new TranslateTransition(dur, iv);
                tt.setToX(step.targetX);
                tt.setToY(step.targetY);
                tt.setInterpolator(interpolator);
                anim = tt;
            }
            case ROTATE -> {
                RotateTransition rt = new RotateTransition(dur, iv);
                rt.setToAngle(step.targetAngle);
                rt.setInterpolator(interpolator);
                anim = rt;
            }
            case SCALE -> {
                ScaleTransition st = new ScaleTransition(dur, iv);
                st.setToX(step.targetScaleX);
                st.setToY(step.targetScaleY);
                st.setInterpolator(interpolator);
                anim = st;
            }
            default ->
                // 其他类型可扩展，比如 Fade
                    anim = new PauseTransition(dur);
        }
        return anim;
    }

    // -------- 内部类：动画步骤定义 --------
    public enum StepType {
        TRANSLATE, ROTATE, SCALE
    }

    public static class AnimationStep {
        StepType type;
        double targetX, targetY;
        double targetAngle;
        double targetScaleX, targetScaleY;
        int durationMs;
        Interpolator interpolator;

        // 私有构造函数，外部通过工厂方法创建
        private AnimationStep(StepType type, int durationMs, Interpolator interpolator) {
            this.type = type;
            this.durationMs = durationMs;
            this.interpolator = interpolator;
        }

        // 位移
        public static AnimationStep translate(double x, double y, int ms, Interpolator e) {
            AnimationStep step = new AnimationStep(StepType.TRANSLATE, ms, e);
            step.targetX = x;
            step.targetY = y;
            return step;
        }

        // 旋转
        public static AnimationStep rotate(double angle, int ms, Interpolator e) {
            AnimationStep step = new AnimationStep(StepType.ROTATE, ms, e);
            step.targetAngle = angle;
            return step;
        }

        // 统一缩放
        public static AnimationStep scale(double scale, int ms, Interpolator e) {
            return scale(scale, scale, ms, e);
        }

        // 分别缩放
        public static AnimationStep scale(double sx, double sy, int ms, Interpolator e) {
            AnimationStep step = new AnimationStep(StepType.SCALE, ms, e);
            step.targetScaleX = sx;
            step.targetScaleY = sy;
            return step;
        }
    }
}