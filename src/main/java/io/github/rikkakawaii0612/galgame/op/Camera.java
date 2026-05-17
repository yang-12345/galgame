package io.github.rikkakawaii0612.galgame.op;

import javafx.scene.canvas.GraphicsContext;

public class Camera {
    AnimatableProperty x, y;        // 摄像机注视点（世界坐标）
    AnimatableProperty zoom;        // 缩放倍数（1 = 原始大小）
    AnimatableProperty rotation;    // 旋转角度（度）

    public Camera(double x, double y) {
        this.x = new AnimatableProperty(x);
        this.y = new AnimatableProperty(y);
        this.zoom = new AnimatableProperty(1);
        this.rotation = new AnimatableProperty(0);
    }

    /**
     * 将摄像机变换应用到 GraphicsContext。
     * 所有节点绘制前调用此方法，建立世界 -> 屏幕坐标映射。
     */
    public void applyTransform(GraphicsContext gc, double canvasWidth, double canvasHeight) {
        // 将坐标系原点移到画布中心
        gc.translate(canvasWidth / 2, canvasHeight / 2);
        // 缩放
        double z = zoom.get();
        gc.scale(z, z);
        // 旋转
        gc.rotate(rotation.get());
        // 平移到摄像机位置（世界坐标偏移，取反）
        gc.translate(-x.get(), -y.get());
    }
}