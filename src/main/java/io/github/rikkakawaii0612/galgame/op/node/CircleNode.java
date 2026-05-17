package io.github.rikkakawaii0612.galgame.op.node;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class CircleNode extends RenderNode {
    private double radius;
    private Color color;

    public CircleNode(double x, double y, double r, Color color) {
        super(x, y);
        this.radius = r;
        this.color = color;
        setPivot(0, 0);  // 圆心为锚点
    }

    @Override
    protected void drawShape(GraphicsContext gc) {
        gc.setFill(color);
        gc.fillOval(-radius, -radius, radius * 2, radius * 2);
    }
}