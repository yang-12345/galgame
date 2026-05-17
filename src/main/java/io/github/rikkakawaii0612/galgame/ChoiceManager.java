package io.github.rikkakawaii0612.galgame;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.util.List;
import java.util.function.Consumer;

public class ChoiceManager {
    private VBox container;

    public ChoiceManager() {
        container = new VBox(40);
        container.setStyle(
                "-fx-background-color: rgba(20,20,20,0.5);" +
                        "-fx-padding: 20;" +
                        "-fx-alignment: center;"
        );
        container.setVisible(false);
    }

    public VBox getView() { return container; }

    public void show(List<StoryNode.Choice> choices, Consumer<StoryNode.Choice> callback) {
        container.getChildren().clear();
        for (StoryNode.Choice c : choices) {
            Button btn = new Button(c.getText());
            btn.setFont(Font.font(20.0D));
            btn.setPrefWidth(225.0D);
            btn.setPrefHeight(60.0D);
            btn.setBackground(Background.fill(Color.gray(0.95D)));
            btn.setOnAction(_ -> callback.accept(c));
            container.getChildren().add(btn);
        }
        container.setOpacity(0);
        Platform.runLater(() -> {
            FadeTransition ft = new FadeTransition(Duration.seconds(0.25D), container);
            ft.setInterpolator(Util.CUBE_IN);
            ft.setToValue(1);
            ft.play();
        });
        container.setVisible(true);
    }

    public void hide() {
        container.setVisible(false);
        container.getChildren().clear();
    }
}