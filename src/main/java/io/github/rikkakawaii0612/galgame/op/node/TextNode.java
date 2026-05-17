package io.github.rikkakawaii0612.galgame.op.node;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class TextNode extends RenderNode {
    private String text;
    private Font font;
    private Color fillColor;
    private TextAlignment alignment;        // 左 / 中 / 右
    private final Text measureHelper;             // 用于测量文本尺寸（缓存）

    public TextNode(double x, double y, String text, Font font, Color fillColor) {
        super(x, y);
        this.text = text;
        this.font = font;
        this.fillColor = fillColor;
        this.alignment = TextAlignment.LEFT;
        this.measureHelper = new Text();
        // 默认锚点为文本左下角（基线起点），用户可根据需要设置 pivot
        setPivot(0, 0);
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public void setAlignment(TextAlignment alignment) {
        this.alignment = alignment;
    }

    public void setFillColor(Color color) {
        this.fillColor = color;
    }

    @Override
    protected void drawShape(GraphicsContext gc) {
        gc.setFont(font);
        gc.setFill(fillColor);

        // 3. 根据对齐方式计算文字绘制偏移（使 pivot 点的行为符合预期）
        if (alignment != TextAlignment.LEFT) {
            // 更新测量用的字体
            measureHelper.setFont(font);
            measureHelper.setText(text);
            double textWidth = measureHelper.getLayoutBounds().getWidth();
            double textHeight = measureHelper.getLayoutBounds().getHeight();

            double offsetX = 0;
            double offsetY = 0;

            if (alignment == TextAlignment.CENTER) {
                offsetX = -textWidth / 2;
            } else if (alignment == TextAlignment.RIGHT) {
                offsetX = -textWidth;
            }
            // 注意：fillText 的 y 是基线位置，向上偏移高度的一半可让 pivot 近似为中心
            // 这里保持基线对齐，若需垂直居中可继续调整 offsetY
            gc.translate(offsetX, offsetY);
        }

        // 4. 绘制文本（x=0, y=0 为当前变换原点）
        gc.fillText(text, 0, 0);
    }
}