package io.github.rikkakawaii0612.galgame.op;

import javafx.scene.canvas.GraphicsContext;

public abstract class RenderNode {
    // 变换属性（世界坐标）
    AnimatableProperty x, y;
    AnimatableProperty rotation;   // 角度制
    AnimatableProperty scaleX, scaleY;
    AnimatableProperty alpha;      // 0..1

    // 旋转/缩放锚点（相对于节点左上角，未变换时的局部坐标）
    double pivotX, pivotY;

    public RenderNode(double x, double y) {
        this.x = new AnimatableProperty(x);
        this.y = new AnimatableProperty(y);
        this.rotation = new AnimatableProperty(0);
        this.scaleX = new AnimatableProperty(1);
        this.scaleY = new AnimatableProperty(1);
        this.alpha = new AnimatableProperty(1);
        this.pivotX = 0;
        this.pivotY = 0;
    }

    public void setPivot(double px, double py) {
        this.pivotX = px;
        this.pivotY = py;
    }

    public void update(double deltaSec) {
        x.update(deltaSec);
        y.update(deltaSec);
        rotation.update(deltaSec);
        scaleX.update(deltaSec);
        scaleY.update(deltaSec);
        alpha.update(deltaSec);
    }

    /**
     * 绘制节点（世界坐标系，摄像机变换已由外部应用）
     */
    public void render(GraphicsContext gc) {
        gc.save();
        // 节点自身变换：先平移，再旋转缩放（基于锚点）
        gc.translate(x.get(), y.get());
        gc.translate(pivotX, pivotY);
        gc.rotate(rotation.get());
        gc.scale(scaleX.get(), scaleY.get());
        gc.translate(-pivotX, -pivotY);

        // 透明度
        gc.setGlobalAlpha(alpha.get());

        // 具体绘制
        drawShape(gc);

        gc.restore();
    }

    protected abstract void drawShape(GraphicsContext gc);
}