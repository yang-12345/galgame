package io.github.rikkakawaii0612.galgame.op.node;

import io.github.rikkakawaii0612.galgame.op.AnimatableProperty;
import javafx.scene.canvas.GraphicsContext;

public abstract class RenderNode {
    public AnimatableProperty x, y, rotation, scaleX, scaleY, alpha;
    double pivotX, pivotY;
    double showTime = 0, hideTime = Double.MAX_VALUE;

    public RenderNode(double x, double y) {
        this.x = new AnimatableProperty(x);
        this.y = new AnimatableProperty(y);
        this.rotation = new AnimatableProperty(0);
        this.scaleX = new AnimatableProperty(1);
        this.scaleY = new AnimatableProperty(1);
        this.alpha = new AnimatableProperty(1);
    }

    public void setPivot(double px, double py) {
        this.pivotX = px;
        this.pivotY = py;
    }

    public boolean isVisibleAt(double globalTime) {
        return globalTime >= showTime && globalTime < hideTime;
    }

    public void render(GraphicsContext gc, double globalTime) {
        double ca = alpha.getValueAt(globalTime);
        if (ca <= 0.0D) {
            return;
        }
        gc.save();
        // 从属性中根据全局时间获取当前值
        double cx = x.getValueAt(globalTime);
        double cy = y.getValueAt(globalTime);
        double cr = rotation.getValueAt(globalTime);
        double csx = scaleX.getValueAt(globalTime);
        double csy = scaleY.getValueAt(globalTime);

        gc.translate(cx, cy);
        gc.translate(pivotX, pivotY);
        gc.rotate(cr);
        gc.scale(csx, csy);
        gc.translate(-pivotX, -pivotY);
        gc.setGlobalAlpha(ca);
        drawShape(gc);
        gc.restore();
    }

    protected abstract void drawShape(GraphicsContext gc);
}