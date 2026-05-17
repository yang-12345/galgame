package io.github.rikkakawaii0612.galgame.op.node;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class RectNode extends RenderNode {
    private double width, height;
    private Color color;

    public RectNode(double x, double y, double w, double h, Color color) {
        super(x, y);
        this.width = w;
        this.height = h;
        this.color = color;
        setPivot(w / 2, h / 2);  // 默认中心为锚点
    }

    @Override
    protected void drawShape(GraphicsContext gc) {
        gc.setFill(color);
        gc.fillRect(0, 0, width, height);
    }
}