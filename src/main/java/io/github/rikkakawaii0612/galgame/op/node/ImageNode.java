package io.github.rikkakawaii0612.galgame.op.node;

import io.github.rikkakawaii0612.galgame.Util;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class ImageNode extends RenderNode {
    private final Image image;
    public double width, height;  // 绘制尺寸（缩放后）

    public ImageNode(double x, double y, String path) {
        this(x, y, new Image(Util.resource(path)));
    }

    public ImageNode(double x, double y, Image image) {
        super(x, y);
        this.image = image;
        this.width = image.getWidth();
        this.height = image.getHeight();
        setPivot(width / 2, height / 2);
    }

    @Override
    protected void drawShape(GraphicsContext gc) {
        // 图像绘制在 0,0 开始
        gc.drawImage(image, 0, 0, width, height);
    }
}