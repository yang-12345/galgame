package io.github.rikkakawaii0612.galgame.op;

import io.github.rikkakawaii0612.galgame.op.node.RenderNode;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleFunction;

public class AnimationCanvas extends Canvas {
    private final List<RenderNode> nodes = new ArrayList<>();
    Camera camera;
    private final AnimationTimer timer;
    private double elapsedTime = 0;   // 全局时间（秒）
    private final double duration;
    private final Runnable onFinishedCallback;

    public AnimationCanvas(double width, double height, double duration, Runnable onFinishedCallback) {
        super(width, height);
        this.camera = new Camera(0, 0);
        this.duration = duration;
        this.onFinishedCallback = onFinishedCallback;

        timer = new AnimationTimer() {
            private long lastFrame = 0;
            @Override
            public void handle(long now) {
                if (lastFrame == 0) {
                    lastFrame = now;
                    return;
                }
                double deltaSec = (now - lastFrame) / 1_000_000_000.0D;
                if (deltaSec > 0.1) deltaSec = 0.016;
                updateAndRender(deltaSec);
                lastFrame = now;
            }
        };
    }

    public void start() {
        this.timer.start();
    }

    private void updateAndRender(double deltaSec) {
        // 累加全局时间
        elapsedTime += deltaSec;
        if (elapsedTime > duration) {
            this.onFinishedCallback.run();
            this.timer.stop();
            return;
        }

        // 摄像机参数更新（内部使用 getValueAt 即可）
        GraphicsContext gc = getGraphicsContext2D();
        gc.clearRect(0, 0, getWidth(), getHeight());
        gc.setFill(Color.BLACK);
        gc.fillRect(0.0D, 0.0D, 1280.0D, 720.0D);
        gc.save();
        applyCameraTransform(gc, elapsedTime);

        for (RenderNode node : nodes) {
            if (node.isVisibleAt(elapsedTime)) {
                node.render(gc, elapsedTime);
            }
        }
        gc.restore();
    }

    private void applyCameraTransform(GraphicsContext gc, double globalTime) {
        double cx = camera.x.getValueAt(globalTime);
        double cy = camera.y.getValueAt(globalTime);
        double cz = camera.zoom.getValueAt(globalTime);
        double crot = camera.rotation.getValueAt(globalTime);
        gc.translate(getWidth()/2, getHeight()/2);
        gc.scale(cz, cz);
        gc.rotate(crot);
        gc.translate(-cx, -cy);
    }

    /**
     * 便捷方法：以当前全局时间为起点，为某个属性添加一段动画。
     */
    public void animateProperty(AnimatableProperty prop, double duration, double target,
                                DoubleFunction<Double> easing) {
        prop.addKeyframe(elapsedTime, elapsedTime + duration, target, easing);
    }

    /** 重置全局时间（例如从头播放动画） */
    public void resetTime() {
        elapsedTime = 0;
    }

    public void addNode(RenderNode node) {
        nodes.add(node);
    }

    public void removeNode(RenderNode node) {
        nodes.remove(node);
    }

    public void toTop(RenderNode node) {
        if (this.nodes.remove(node)) {
            this.nodes.addLast(node);
        }
    }
}