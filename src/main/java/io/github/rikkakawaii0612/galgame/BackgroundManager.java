package io.github.rikkakawaii0612.galgame;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.InputStream;

public class BackgroundManager {
    private Pane pane;
    private Canvas canvas;

    public BackgroundManager() {
        canvas = new Canvas(1280, 720);
        pane = new Pane(canvas);
        pane.setBackground(Background.fill(Color.BLACK));
    }

    public Pane getView() {
        return pane;
    }

    public void setImage(String imagePath) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Image img = new Image(getClass().getResourceAsStream(imagePath));
        double ratio = Math.min(img.getWidth() / 1281.28D, img.getHeight() / 720.72D);
        Image image = new Image(getClass().getResourceAsStream(imagePath), img.getWidth() / ratio, img.getHeight() / ratio, true, true);
        gc.clearRect(0, 0, 1280, 720);
        gc.drawImage(image, (1280.0D - image.getWidth()) / 2.0D, (720.0D - image.getHeight()) / 2.0D);
    }

    public GraphicsContext getGraphicsContext() {
        return canvas.getGraphicsContext2D();
    }
}